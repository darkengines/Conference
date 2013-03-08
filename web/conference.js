setRTCPeerConnection();
setGetUserMedia();

function setRTCPeerConnection() {
    var RTCPeerConnection = null;
    var rtccheck = new Array();
    rtccheck.push(typeof webkitRTCPeerConnection == "undefined");
    rtccheck.push(typeof mozRTCPeerConnection == "undefined");

    var i = 0;
    var found = 0;
    while (!found && i<rtccheck.length) {
	found = !rtccheck[i];
	i++;
    }
    if (found) {
	switch (i) {
	    case (1): {
		RTCPeerConnection = webkitRTCPeerConnection;
		break;
	    }
	    case (2): {
		RTCPeerConnection = function(arg) {
		    var r = new mozRTCPeerConnection(arg);
		    r.setLocalDescription = function(localDescription) {
			r.localDescription = localDescription;
		    };
		    return r;
		}
		window.RTCSessionDescription = function(e) {return mozRTCSessionDescription(e.type, e.sdp)};
		window.RTCIceCandidate = mozRTCIceCandidate;
		break;
	    }
	}
	window.RTCPeerConnection = RTCPeerConnection;
    }
}

function setGetUserMedia() {
    var getUserMedia = null;
    var gumcheck = new Array();
    gumcheck.push(typeof navigator.webkitGetUserMedia == "undefined");
    gumcheck.push(typeof navigator.mozGetUserMedia == "undefined");

    var i = 0;
    var found = 0;
    while (!found && i<gumcheck.length) {
	found = !gumcheck[i];
	i++;
    }
    if (found) {
	switch (i) {
	    case (1): {
		getUserMedia = navigator.webkitGetUserMedia;
		break;
	    }
	    case (2): {
		getUserMedia = navigator.mozGetUserMedia;
		break;
	    }
	}
	navigator.getUserMedia = getUserMedia;
    }
}

(function() {
    $(document).ready(function() {
		//initialize();
		$('div.Setup').each(function() {
		   var $container = $(this);
		   $('a.Setup', $container).each(function() {
			   var $a = $(this);
			   $a.click(function() {
			  $.ajax({
				  url: $a.attr('href'),
				  success: function(content) {
				  $('p.Result', $container).text(content);
				  }
			  });
			  return false;
			   });
		   }) 
		});
		$('div.Join').each(function() {
		   var $container = $(this);
		   $('form.Join', $container).each(function() {
			   var $form = $(this);
			   $('input[type=submit]', $form).click(function() {
				  $.ajax({
					  url: $form.attr('action'),
					  data: $form.serialize(),
					  success: function(content) {
					  $('p.Result', $container).text(content);
					  }
				  });
				  return false;
			   });
		   }) 
		});
		$('div.Login').each(function() {
		   var $container = $(this);
		   $('form.Login', $container).each(function() {
			   var $form = $(this);
			   $('input[type=submit]', $form).click(function() {
				  $.ajax({
					  url: $form.attr('action'),
					  data: $form.serialize(),
					  success: function(content) {
						if (content.code) {
							$('p.Result', $container).text(content.content.message);
						} else {
							$('p.Result', $container).text(content.content.uuid);
							$.cookie('uuid', content.content.uuid);
							$.cookie('id', content.content.userId);
							window.location = "nexus.html";
						}
					  }
				  });
				  return false;
			   });
		   }) 
		});
		$('div.Chat').each(function() {
		   var $container = $(this);
		   $('a.Chat', $container).each(function() {
			   var $a = $(this);
			   $a.click(function() {
				var socket = new WebSocket('ws://127.0.0.1:8080/conference/websocket');
				socket.onmessage = function(e) {
					$('p.Result', $container).append($('<p>'+e.data+'</p>'));
				}
				return false;
			   });
		   }) 
		});
    });
})(jQuery);

function initialize() {
	console.log('initializing...');
	console.log('initializing media');
	
	var mediaConfig = {'video':true};
	navigator.getUserMedia(mediaConfig, onUserMediaSuccess, function(e) {});

}

var localStream = null;

function attachMediaStream(stream) {
	localStream = stream;
	console.log("Attaching media stream");
	var localMedia = $('video.LocalMedia').get(0);
	if (localMedia.mozSrcObject !== undefined) {
        localMedia.mozSrcObject = stream;
    } else {
        localMedia.src = (window.URL && window.URL.createObjectURL(stream)) || stream;
    }
	localMedia.play();
}

var remoteMedia = null;
var remoteStream = null;
var pc1 = null;
var pc2 = null;


function onUserMediaSuccess(stream) {
	console.log('getUserMedia success');
	attachMediaStream(stream);
	createPeers();
}

function createPeers() {
	var pc_config = {"iceServers": [{"url": "stun:stun.l.google.com:19302"}]};
	pc1 = new RTCPeerConnection(null);
	pc1.onicecandidate = function(event) {
		if (event.candidate) {
			pc2.addIceCandidate(event.candidate);
		}
	};
	pc1.addStream(localStream);
	pc1.createOffer(onGotLocalDescription);
}

function getCandidatesFromSdp(sdp) {
    var mRegExp = new RegExp('m=(.*?) ', 'g');
    var candidateRegExp = new RegExp('(a=candidate.*)', 'g');
    
    var ms = new Array();
    var candidates = new Array();
    var match = null;
    
    var i = 0;
    
    while ((match = mRegExp.exec(sdp)) !== null) {
	if (match[1] == 'video') {
	    ms.push({
		index: i,
		offset: mRegExp.lastIndex,
		name: match[1]
	    });
	    i++;
	}
    }
    var msLength = ms.length;
    var found = 0;
    while ((match = candidateRegExp.exec(sdp)) !== null) {
	i = msLength;
	found = 0;
	while (!found && i--) {
	    found = candidateRegExp.lastIndex > ms[i].offset;
	}
	if (found) {
	    candidates.push(new RTCIceCandidate(match[0], ms[i].name, ms[i].index));
	}
    }
    return candidates;
}
var glocalDescription = null;
var gremoteDescription = null;
function onGotRemoteDescription(remoteDescription) {
	
	pc1.setRemoteDescription(remoteDescription);
	pc2.localDescription = remoteDescription;
	var candidates = getCandidatesFromSdp(glocalDescription.sdp);
	var len = candidates.length;
	while (len--) {
	    pc2.addIceCandidate(candidates[len]);
	}
	candidates = getCandidatesFromSdp(remoteDescription.sdp);
	len = candidates.length;
	while (len--) {
	    pc1.addIceCandidate(candidates[len]);
	}
}

function onGotLocalDescription(localDescription) {
	
	pc2 = new RTCPeerConnection(null);
	pc2.onicecandidate = function(event) {
		if (event.candidate) {
			pc1.addIceCandidate(event.candidate);
		}
	};
	pc2.onaddstream = function(event) {
		remoteMedia = $('video.RemoteMedia').get(0);
		remoteStream = event.stream;
		remoteMedia.src = webkitURL.createObjectURL(event.stream);
		remoteMedia.play();
	};
	pc2.setRemoteDescription(localDescription);
	pc1.localDescription = localDescription;
	glocalDescription = localDescription
	pc2.createAnswer(onGotRemoteDescription);
}

function setCookie(c_name,value,exdays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}

function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
{
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}
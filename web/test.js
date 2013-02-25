
var socket = null;
(function() {
    $(document).ready(function() {
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
							$('p.Result', $container).append($('<p>'+content.content.uuid+'</p>'));
							$.cookie('uuid', content.content.uuid);
							socket = new WebSocket('ws://192.168.1.2:8080/conference/websocket');
							socket.onmessage = function(e) {
								$('p.Result', $container).append($('<p>'+e.data+'</p>'));
							}
							socket.onopen = function() {
								var pc = new RTCPeerConnection(null);
								pc.createOffer(onGotLocalDescription);
							}
						}
					  }
				  });
				  return false;
			   });
		   }) 
		});
    });
})(jQuery);

function userMediaError(error) {

}

function onGotLocalDescription(localDescription) {
	var userInfo = {
		uuid: $.cookie('uuid'),
		sessionDescription: localDescription
	};
	socket.send(JSON.stringify(userInfo));
}

function initialize() {
	console.log('initializing...');
	console.log('initializing media');
	
	var mediaConfig = {'video':true, 'audio': true};
	navigator.webkitGetUserMedia(mediaConfig, onUserMediaSuccess);

}

var localStream = null;

function attachMediaStream(stream) {
	localStream = stream;
	console.log("Attaching media stream");
	var localMedia = $('video.LocalMedia').get(0);
	localMedia.src = webkitURL.createObjectURL(stream);
};

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
	pc1 = new webkitRTCPeerConnection(null);
	pc1.onicecandidate = function(event) {
		if (event.candidate) {
			pc2.addIceCandidate(event.candidate);
		}
	};
	pc1.addStream(localStream);
	pc1.createOffer(onGotLocalDescription);
}

function onGotRemoteDescription(remoteDescription) {
	pc1.setRemoteDescription(remoteDescription);
	pc2.setLocalDescription(remoteDescription);
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

	switch (i) {
		case (1): {
			RTCPeerConnection = webkitRTCPeerConnection;
			break;
		}
		case (2): {
			RTCPeerConnection = mozRTCPeerConnection;
			break;
		}
	}
	window.RTCPeerConnection = RTCPeerConnection;
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
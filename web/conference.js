(function() {
    $(document).ready(function() {
		initialize();
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
						if (content.error) {
							$('p.Result', $container).text(content.message);
						} else {
							$.cookie('token', content.uuid);
						}
					  },
				  });
				  return false;
			   });
		   }) 
		});
    });
})(jQuery);

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

function onGotLocalDescription(localDescription) {
	pc2 = new webkitRTCPeerConnection(null);
	
	pc2.onicecandidate = function(event) {
		if (event.candidate) {
			pc1.addIceCandidate(event.candidate);
		}
	};
	
	pc2.onaddstream = function(event) {
		remoteMedia = $('video.RemoteMedia').get(0);
		remoteStream = event.stream;
		remoteMedia.src = webkitURL.createObjectURL(event.stream);
	};
	pc2.setRemoteDescription(localDescription);
	pc1.setLocalDescription(localDescription);
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
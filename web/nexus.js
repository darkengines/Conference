(function($) {
    $(document).ready(function() {
	
	var ChatMessageNotificationHtmlTemplate = '<div class="ChatMessageNotification"></div>';
	
	var $chatOutput = $('div.Chat div.Output');
	var $chaCallNotification = $('div.Chat div.Output div.Notification .CallNotification');
	var $chatMessageNotification = $('div.Chat div.Output div.Notification .MessageNotification');
	var $chatOutputText = $('div.Chat div.Output .TextContent');
	var $chatOutputHeader = $('div.Chat div.Header');
	var $chatInputText = $('div.Chat div.Input');
	var $camera = $('div.Chat div.Options a.Camera');
	var $users = $('div.Users');
	var $localVideo = $('<video class="Local" autoplay="true" muted="true" controls="true"></video>')
	var selectedRecipient = null;
	var $engine = $.engine();
	
	$camera.click(function() {
	    if (selectedRecipient != null) {
		selectedRecipient.call({
		    
		    })
	    }
	    return false;
	})
	
	$chatInputText.keyup(function(e) {
	    if (selectedRecipient != null) {
		if (e.keyCode == 13) {
		    selectedRecipient.sendChatMessage($chatInputText.text());
		    $chatInputText.empty();
		    return false;
		}
	    } 
	});
	
	$engine.onlineUsers = function(users) {
	    $(users).each(function(index, user) {
		addUser(user)
	    });
	};
	$engine.localStreamAdded= function(stream) {
	    var media = $('<video controls="true" autoplay="autoplay" class="Local" muted="true"/>').get(0);
	    attachMediaStream(media, stream);
	    $chatOutputHeader.append($(media));
	    media.play();
	};
	$engine.onlineUser = function(user) {
	    addUser(user)
	}
	function addUser(user) {
	    var $user = $('<div class="Contrast User"><table><tr><td class="DisplayName">'+user.displayName+'</td><td class="ChatMessageNotification"></td></tr></table></div>');
	    user.label = $user;
	    $user.click(function() {
		selectedRecipient = user;
		selectedRecipientChanged(selectedRecipient);
	    });
	    user.offline = function() {
		$user.remove();
	    };
	    user.chatMessage = function(pendingChatMessages) {
		if (selectedRecipient!=null && selectedRecipient.id == user.id) {
		    writePendingChatMessages(pendingChatMessages);
		} else {
		    refreshUserLabel(user);
		}
	    };
	    user.streamAdded = function(stream) {
		if (selectedRecipient!=null && selectedRecipient.id == user.id) {
		    setRemoteStream(stream);
		}
	    };
	    user.streamRemoved = function(stream) {
		if (selectedRecipient!=null && selectedRecipient.id == user.id) {
		    user.stream = null;
		    removeRemoteStream(user);
		}
	    };
	    user.onHangUp = function() {
		alert('handeg up');
	    };
	    user.answered = function() {
		$chaCallNotification.empty();
		var $hangup = $('<a href="false">Hang up</a>');
		$hangup.click(function() {
		    user.hangUp();
		    return false;
		});
		$chaCallNotification.append($('<span>Communication established</span>')).append($hangup);
	    };
	    user.offer = function() {
		if (selectedRecipient!=null && selectedRecipient.id == user.id) {
		    displayCallForm(user);
		}
	    };
	    $users.append($user);
	}
	function removeRemoteStream(user) {
	    var $video = $('video.Remote', $chatOutput);
	    if ($video.length) {
		$video.get(0).pause();
		$video.get(0).stop();
		$video.remove();
	    }
	}
	function displayCallForm(user) {
	    var $question = $('<span>Answer to '+user.displayName+'\'s call ? </span>');
	    var $yes = $('<a href="#">Accept</a>');
	    var $no = $('<a style="margin-left: 10px;" href="#">Reject</a>');
	    $yes.click(function() {
		user.answer();
		$chaCallNotification.empty();
		var $hangup = $('<a href="false">Hang up</a>');
		$hangup.click(function() {
		    user.hangUp();
		    return false;
		});
		$chaCallNotification.append($('<span>Communication established</span>')).append($hangup);
		return false;
	    });
	    $no.click(function() {
		$chaCallNotification.empty();
		return false;
	    })
	    $chaCallNotification.empty();
	    $chaCallNotification.append($question).append($yes).append($no);
	}
	function setRemoteStream(stream) {
	    var $videos = $('video.Remote', $chatOutputHeader);
	    var video = null;
	    if (!$videos.length) {
		video = $('<video autoplay="autoplay" controls="true" class="Remote"/>').get(0);
		$chatOutputHeader.append($(video));
	    } else {
		video = $videos.get(0);
	    }
	    attachMediaStream(video, stream);
	    video.play;
	}
	function writePendingChatMessages(pendingChatMessages) {
	    while (pendingChatMessages.length > 0) {
		var message = pendingChatMessages.pop();
		$chatOutputText.append($('<p class="Text">'+message.author.displayName+': '+message.content+'</p>'));
	    }
	    $chatOutput.animate({
		scrollTop: $chatOutput[0].scrollHeight
	    }, 100);
	}
	function refreshUserLabel(user) {
	    var pendingMessagesLength = user.pendingChatMessages.length;
	    $('.ChatMessageNotification', user.label).each(function(index, element) {
		var $notification = $(element);
		if (pendingMessagesLength) {
		    $notification.text('('+pendingMessagesLength+')');
		    $notification.effect("bounce", "slow");
		} else {
		    $notification.text('');
		}
	    })
	    
	}
	function selectedRecipientChanged(recipient) {
	    clearChat();
	    writePendingChatMessages(recipient.pendingChatMessages);
	    if (recipient.stream != null) {
		setRemoteStream(recipient.stream);
	    } else {
		removeRemoteStream(recipient);
	    }
	    if (recipient.pendingOffer != null) {
		displayCallForm(recipient);
	    } else {
		$chaCallNotification.empty();
	    }
	    refreshUserLabel(recipient);
	}
	function refreshChat() {
	    refreshStreams();
	    refreshMessages();
	    refreshNotifications();
	}
	
	function refreshStreams() {
	    var user = selectedRecipient;
	    if (user.receivingLocalStream && engine.localStream != null) {
		
	    }
	}
	
	function clearChat() {
	    $('.Text', $chatOutputText).remove();
	    $chatInputText.empty();
	}
    });
})(jQuery);
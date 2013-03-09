(function() {
    $(document).ready(function() {
	//initialize();
	$('div.Field').each(function() {
	    var $container = $(this);
	    var $label = $('label', $container);
	    var $input = $('input[type=text], input[type=password]', $container);
	    $input.bind('keydown', function() {
		$label.hide();
	    });
	    $input.bind('blur keyup', function() {
		if ($(this).val() != '') {
		    $label.hide();
		} else {
		    $label.show();
		}
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
    });
})(jQuery);
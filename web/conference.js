(function() {
    $(document).ready(function() {
	$('div.Setup').each(function() {
	   var $container = $(this);
	   $('a.Setup', $container).each(function() {
	       var $a = $(this);
	       $a.click(function() {
		  $.ajax({
		      url: $a.attribute('href'),
		      success: function() {
			  $('p.Result', $container).text('OK !');
		      }
		  });
		  alert('caca');
		  return false;
	       });
	   }) 
	});
    });
})(jQuery);
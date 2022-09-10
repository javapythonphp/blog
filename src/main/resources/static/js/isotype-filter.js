// init Isotope
var $grid = $('.grid').isotope({

});


// bind filter button click
$('#filters').on( 'click', 'button', function() {
  var filterValue = $( this ).attr('data-filter');
  $grid.isotope({ filter: filterValue });

});

// bind sort button click


// change is-checked class on buttons

  

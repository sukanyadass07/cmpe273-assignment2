$(":button").click(function() {
    var isbn = this.id;
    alert('About to report lost on ISBN ' + isbn);
    
    $.ajax({
        type: "PUT",
        url: "v1/books/"+ isbn + "?status=lost",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
           alert("status received");
           window.location.reload(true);
           $('#bookTable tr').each(function() {
        	    var STATUS = $(this).find("td").eq(4).html(); 
        	    if(STATUS == "lost")
        	           $(":button").attr("disabled", true);
        	});
           
          
        },
    
        error: function (jqXHR, status) {
        	 alert("error in status");
    
        } 
        
    });     

});
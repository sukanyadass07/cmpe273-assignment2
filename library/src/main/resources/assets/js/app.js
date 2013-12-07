$(":button").click(function() {
    var isbn = this.id;  
    alert('About to report lost on ISBN ' + isbn);
    
    $.ajax({
        type: "PUT",
        url: "v1/books/"+ isbn + "?status=lost",
        contentType: "application/json; charset=utf-8",
        success: function (data) {
           alert("status received");
           var buttonId="#"+isbn;
           var statusID = "#status"+isbn;
           $(statusID).text("lost");
           $(buttonId).prop("disabled",true);
 
        },
    
        error: function (jqXHR, status) {
        	 alert("error in status");
    
        } 
        
    });     

});
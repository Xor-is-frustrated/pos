
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}


//BUTTON ACTIONS
var id=-1;
var order_open=false;
var inventoryQuantity=0;
var price=0;


function purchase(event)
{
	var baseUrl = $("meta[name=baseUrl]").attr("content");

	var url = baseUrl+"/api/order/purchase/"+id;

	
	
	$.ajax({
		url: url,
		type: 'GET',

		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			// $("#success-message").text("Order Purchased");
			successPopup("Order Purchased");
			order_open=false;
			if(id!=-1 && order_open==false){
				$("#receipt").prop('disabled', false);
				$("#add-order").prop('disabled', true);

				$('[id=edit-item]').slice(0).prop("disabled", true);
				$('[id=delete-item]').slice(0).prop("disabled", true);
				
			}
			$('#purchase').prop('disabled',true);
    		
    		$("#inputSellingPrice").prop('disabled', true);
				$("#inputBarcode").prop('disabled', true);
				$("#inputQuantity").prop('disabled', true);
		},
		error: handleAjaxError
	});

}

function receipt(event)
{
	var baseUrl = $("meta[name=baseUrl]").attr("content");

	var url = baseUrl+"/api/order/receipt/"+id;
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.responseType = "blob";

	req.onload = function (event) {
		var blob = req.response;
		console.log(blob.size);
		var link=document.createElement('a');
		link.href=window.URL.createObjectURL(blob);
		link.download="Receipt.pdf";
		link.click();
		
	};

	req.send();

}

function addOrder(event){
	//Set the values to update
	var $form = $("#order-form");
	var json = toJson($form);
	var url = getOrderUrl();
	console.log("in add order");
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			$( '#order-form' ).each(function(){
   			 this.reset();
				});
			getOrderList();  
		},
		error: handleAjaxError
	});

	return false;
}

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-edit-form input[name=id]").val();	
	var url = getOrderUrl() + "/" + id;
	console.log(url);
	//Set the values to update
	var $form = $("#order-edit-form");
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			getOrderList();   
		},
		error: handleAjaxError
	});

	return false;
}


function getOrderList(){
	var url = getOrderUrl() + "/order/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {

			displayOrderList(data);  
		},
		error: handleAjaxError
	});
}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
		url: url,
		type: 'DELETE',
		success: function(data) {
			getOrderList();  
		},
		error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#OrderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getOrderUrl();

	//Make ajax call
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			uploadRows();  
		},
		error: function(response){
			row.error=response.responseText
			errorData.push(row);
			uploadRows();
		}
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayOrderList(data){
	var $thead = $('#order-table').find('thead');
	var $tbody = $('#order-table').find('tbody');

	$tbody.empty();
	$thead.empty();
	if(data.length==0){
		$("#purchase").prop('disabled', true);
		return false;
	}
	$("#purchase").prop('disabled', false);
	var row = '<tr>'
		+ '<th scope="col">Product</th>'
		+ '<th scope="col">Quantity</th>'
		+ '<th scope="col">Price</th>'
		+ '<th scope="col">Amount</th>'
		+ '<th scope="col">Actions</th>'
		+ '</tr>';
		$thead.append(row);
	for(var i in data){
		var e = data[i];
		var buttonHtml ='<button class="btn btn-dark btn-sm" id="edit-item" onclick="displayEditOrder(' + e.id + ')">Edit</button>'
		buttonHtml +=  ' <button class="btn btn-dark btn-sm" id="delete-item" onclick="deleteOrder(' + e.id + ')">Delete</button>'
		var row = '<tr>'

		+ '<td>' + e.product + '</td>'
		+ '<td> '  + e.quantity + '</td>'
		+ '<td> '  +  e.sellingPrice.toFixed(2) + '</td>'
		+ '<td> '  +  e.itemTotalCost.toFixed(2) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
		$tbody.append(row);
	}
}

function displayEditOrder(id){
	var url = getOrderUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayOrder(data);   
		},
		error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#orderFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#orderFileName').html(fileName);
}

function displayUploadData(){
	resetUploadDialog(); 	
	$('#upload-order-modal').modal('toggle');
}

function displayOrder(data){
	
	$("#order-edit-form input[name=quantity]").val(data.quantity);
	$("#order-edit-form input[name=barcode]").val(data.barcode);		
	$("#order-edit-form input[name=id]").val(data.id);		
	$("#order-edit-form input[name=orderId]").val(id);
	$("#order-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$("#update-order").prop("disabled", true);
	inventoryQuantity=data.quantity;
	price=data.sellingPrice;
	$('#edit-order-modal').modal('toggle');
}

function checkToCreateOrder(){
	if(id!=-1 && order_open== true){
		$('#discard-order-modal').modal('toggle');
	}
	else{
		createOrder();
	}
}

function createOrder(){



	var baseUrl = $("meta[name=baseUrl]").attr("content");
	var url = baseUrl+"/api/order";
	$.ajax({
		url: url,
		type: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			console.log(response);
			id=response;
			order_open=true;
			$("#order-form input[name=orderId]").val(id);

			
				$("#receipt").prop('disabled', true);
				$("#inputSellingPrice").prop('disabled', false);
				$("#inputBarcode").prop('disabled', false);
				$("#inputQuantity").prop('disabled', false);
				

			
			$("#order-id").text("Order Number: "+id);
			getOrderList();  
		},
		error: handleAjaxError
	});

	return false;
}

function deleteWholeOrder(createOrder){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	var url = baseUrl+"/api/order/"+id;

	$.ajax({
		url: url,
		type: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			console.log(response);
			id=-1;
			order_open=false;
			createOrder();
		},
		error: handleAjaxError
	});

}


function discardOrder(){
	$('#discard-order-modal').modal('toggle');
	deleteWholeOrder(createOrder);
	
}
//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#purchase').click(purchase);
	$('#receipt').click(receipt);
	$('#create-order').click(checkToCreateOrder);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
	$('#discard-order').click(discardOrder);
	$('#orderFile').on('change', updateFileName);


}

function checkNumeric(){
	var value=$('#order-form input[name=sellingPrice]').val();
  if(( value.length ==   0) || ($.isNumeric(value))) 
 	{
 		
 		 var value=$('#order-form input[name=quantity]').val();
		  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
		 	{
		 		return true;
		 		
		 	}
 		
 	}
 	return false;
  
}

function checkEditForm(){
	var value=$('#order-edit-form input[name=quantity]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		var value=$('#order-edit-form input[name=sellingPrice]').val();
	  if(( value.length ==   0) || ( $.isNumeric(value))) 
	 	{
	 		return true;
	 	}
 	}
 	return false;

}

$(document).ready(init);

$('#order-form input[name=barcode],#order-form input[name=sellingPrice],#order-form input[name=quantity]').on("input",function(){

  if ($('#order-form input[name=barcode]').val().length   >   0   &&
        $('#order-form input[name=quantity]').val().length  >   0   &&
        $('#order-form input[name=sellingPrice]').val().length  >   0  && checkNumeric()) {
        $("#add-order").prop("disabled", false);
    }
    else {
        $("#add-order").prop("disabled", true);
    }
});


$('#order-edit-form input[name=quantity],#order-edit-form input[name=sellingPrice]  ').on("input",function(){
  	
	var quantity=$('#order-edit-form input[name=quantity] ').val();
	var sp=$('#order-edit-form input[name=sellingPrice] ').val();

    if (quantity.length  >   0  && sp.length>0 && checkEditForm() && ( quantity!=inventoryQuantity || sp !=price) ){
        $("#update-order").prop("disabled", false);
    }
    else {
        $("#update-order").prop("disabled", true);
    }
  
});


$('#order-form input[name=sellingPrice]').on("input",function(){
	var value=$('#order-form input[name=sellingPrice]').val();
  if(( value.length ==   0) || ($.isNumeric(value))) 
 	{
 		$("#check-price").text("");
 		 
 		
 	}
 	else{
 		$("#check-price").text("Please enter Numeric value");
 		$("#add-order").prop("disabled", true);
 		
 	}
  
});

$('#order-form input[name=quantity]').on("input",function(){
	var value=$('#order-form input[name=quantity]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-quantity").text("");
 		
 	}
 	else{
 		$("#check-quantity").text("Please enter Integer value");
 		$("#add-order").prop("disabled", true);
 	}
  
});

$('#order-edit-form input[name=quantity]').on("input",function(){
	var value=$('#order-edit-form input[name=quantity]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-edit-quantity").text("");
 		
 	}
 	else{
 		$("#check-edit-quantity").text("Please enter Integer value");
 		$("#update-order").prop("disabled", true);
 	}
  
});


$('#order-edit-form input[name=sellingPrice]').on("input",function(){
	var value=$('#order-edit-form input[name=sellingPrice]').val();
  if(( value.length ==   0) || ( $.isNumeric(value))) 
 	{
 		$("#check-edit-sellingPrice").text("");
 		
 	}
 	else{
 		$("#check-edit-sellingPrice").text("Please enter Numeric value");
 		$("#update-order").prop("disabled", true);
 	}
  
});






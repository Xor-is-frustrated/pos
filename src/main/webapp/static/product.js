
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}
// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;
var successCount =0;

var productName="";
var productBarcode="";
var productMrp=0;


function addProduct(event){
	//Set the values to update
	
	var $form = $("#product-add-form");
	var json = toJson($form);
	
	var url = getProductUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		$('#add-product-modal').modal('toggle');	
	   		successPopup("Product is added");
	   		$( '#product-add-form' ).each(function(){
   			 this.reset();
				});
	   		getProductList();  
	   },
	   error: handleAjaxError

	});
	
	return false;
}

function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();  
	   },
	   error: handleAjaxError
	});
}

function updateProduct(event){
	
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();	
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   	$('#edit-product-modal').modal('toggle');
	   		successPopup("Product is updated");
	   		getProductList();   
	   },
	   error: handleAjaxError
	});

	return false;
}
//UI DISPLAY METHODS
function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);   
	   },
	   error: handleAjaxError
	});	
}

function displayProduct(data){
	$("#product-edit-form input[name=product]").val(data.product);	
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
	$("#product-edit-form input[name=mrp]").val(data.mrp);	
	$("#product-edit-form input[name=brandId]").val(data.brandId);	
	$("#product-edit-form input[name=id]").val(data.id);
	$("#update-product").prop('disabled', true);
	productName=data.product;
	productMrp=data.mrp;
	productBarcode=data.barcode;	
	$('#edit-product-modal').modal('toggle');
}

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		
		var buttonHtml = ' <button class="btn btn-dark btn-sm" onclick="displayEditProduct(' + e.id + ')">Edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>'  + e.barcode + '</td>'
		+ '<td>'  + e.mrp.toFixed(2) + '</td>'
		+ '<td>'  + e.brandId + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	successCount=0;
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$("#errorCount").css("color", "black");
	if(errorData.length>0){
		$("#errorCount").css("color", "red");
	}
	$('#successCount').html("" + successCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#process-data').prop('disabled',false);
	$("#download-errors").prop('disabled', true);
	$('#productFileName').html(fileName);
	processCount = 0;
	successCount=0;
	fileData = [];
	errorData = [];
	$('#rowCount').html("" + fileData.length);
	$('#successCount').html("" + successCount);
	$("#errorCount").css("color", "black");
$('#errorCount').html("" + errorData.length);
}

function displayUploadData(){
 	resetUploadDialog(); 	
 	$("#download-errors").prop('disabled', true);
 	$("#process-data").prop('disabled', true);
	$('#upload-product-modal').modal('toggle');
}

function processData(){
	var file = $('#productFile')[0].files[0];
	$('#process-data').prop('disabled',true);
//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	successCount=0;
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	console.log("readFileDataCallback");
	console.log("the length of  the file is");
	console.log(fileData.length);
	if(fileData.length>5000)
	{
		alert("the tsv file is too big, please reduce the number of rows to less than 5");
		return ;
	}
	
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	if(fileData.length>10)
	{
		alert("more than 10");
		return ;
	}
	//If everything processed then return
	if(processCount==fileData.length){
		getProductList();
		var $file = $('#productFile');
			$file.val('');
			$('#productFileName').html("Choose File");
		if(errorData.length>0){
			$("#download-errors").prop('disabled', false);
		}
		else{
			$("#download-errors").prop('disabled', true);
			
		}

		
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   	successCount++;
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

function DisplayAddProduct(){
	$("#add-product").prop('disabled', true);
	$( '#product-add-form' ).each(function(){
   			 this.reset();
				});
	$('#add-product-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#add-data').click(DisplayAddProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
    
}

$(document).ready(init);
$(document).ready(getProductList);

function checkAddForm(){
	var value=$('#product-add-form input[name=brandId]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) {
  	 value=$('#product-add-form input[name=mrp]').val();
	  if(( value.length ==   0) || ($.isNumeric(value))) 
	 	{
	 		return true;
	 	}
  }
  return false;
}

function checkEditForm(){
	var value=$('#product-edit-form input[name=mrp]').val();
  if(( value.length ==   0) || ($.isNumeric(value))) 
 	{
 		return true;
 		
 	}
 	return false;
}

$('#product-edit-form input[name=barcode],#product-edit-form input[name=product],#product-edit-form input[name=mrp] ').on("input",function(){
  var barcode=$('#product-edit-form input[name=barcode]').val();
  var product=$('#product-edit-form input[name=product]').val();
  var mrp=$('#product-edit-form input[name=mrp]').val();
  if(barcode.length>0 && product.length>0 && mrp.length>0 && checkEditForm() &&(barcode!=productBarcode || product!=productName || mrp!=productMrp)){
  	$("#update-product").prop("disabled", false);
  }
    else {
        $("#update-product").prop("disabled", true);
    }
});

$('#product-add-form input[name=barcode],#product-add-form input[name=brandId],#product-add-form input[name=product],#product-add-form input[name=mrp] ').on("input",function(){

  if ($('#product-add-form input[name=barcode]').val().length   >   0   &&
        $('#product-add-form input[name=brandId]').val().length  >   0   &&
        $('#product-add-form input[name=mrp]').val().length  >   0   &&
        $('#product-add-form input[name=product]').val().length    >   0 && checkAddForm()) {
        $("#add-product").prop("disabled", false);
    }
    else {
        $("#add-product").prop("disabled", true);
    }
});

$('#product-add-form input[name=brandId]').on("input",function(){
	var value=$('#product-add-form input[name=brandId]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-brandId").text("");
 		
 	}
 	else{
 		$("#check-brandId").text("Please enter Integer value");
 		$("#add-product").prop("disabled", true);
 	}
  
});

$('#product-add-form input[name=mrp]').on("input",function(){
	var value=$('#product-add-form input[name=mrp]').val();
  if(( value.length ==   0) || ($.isNumeric(value))) 
 	{
 		$("#check-mrp").text("");
 		
 	}
 	else{
 		$("#check-mrp").text("Please enter Numeric value");
 		$("#add-product").prop("disabled", true);
 	}
  
});

$('#product-edit-form input[name=mrp]').on("input",function(){
	var value=$('#product-edit-form input[name=mrp]').val();
  if(( value.length ==   0) || ($.isNumeric(value))) 
 	{
 		$("#check-edit-mrp").text("");
 		
 	}
 	else{
 		$("#check-edit-mrp").text("Please enter Numeric value");
 		$("#update-product").prop("disabled", true);
 	}
  
});
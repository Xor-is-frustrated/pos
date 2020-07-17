
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;
var successCount =0;
var inventoryQuantity=0;

//BUTTON ACTIONS
function addInventory(event){
	//Set the values to update
	
	var $form = $("#inventory-add-form");
	
	var json = toJson($form);
	var url = getInventoryUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   	$( '#inventory-add-form' ).each(function(){
   			 this.reset();
				});
	   	$('#add-inventory-modal').modal('toggle');

	   		successPopup("Inventory is added");
	   		getInventoryList();  
	   },
	   error: handleAjaxError
	   
	});
	
	return false;
}

function updateInventory(event){
	
	//Get the ID
	var id = $("#inventory-edit-form input[name=id]").val();	
	var url = getInventoryUrl() + "/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   	$('#edit-inventory-modal').modal('toggle');
	   		successPopup("Inventory is updated");
	   		getInventoryList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteInventory(id){
	var url = getInventoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getInventoryList();  
	   },
	   error: handleAjaxError
	});
}



function processData(){
	$('#process-data').prop('disabled',true);
	var file = $('#inventoryFile')[0].files[0];
	processCount = 0;
	successCount=0;
	fileData = [];
	errorData = [];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	console.log("readFileDataCallback");
	console.log("the length of  the file is");
	console.log(fileData.length);
	if(fileData.length>5000)
	{
		alert("the Tsv file is too big, please reduce the number of rows to less than 5000");
		return ;
	}
	
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	if(fileData.length>10)
	{
		alert("more than 10 columns");
		return ;
	}
	//If everything processed then return
	if(processCount==fileData.length){
		getInventoryList();
		var $file = $('#inventoryFile');
			$file.val('');
			$('#inventoryFileName').html("Choose File");
		if(errorData.length>0){
			$("#download-errors").prop('disabled', false);
		}
		else{
			$("#download-errors").prop('disabled', true);
			
		}

	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getInventoryUrl();

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

//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		
		var buttonHtml = ' <button class="btn btn-dark btn-sm" onclick="displayEditInventory(' + e.id + ')">Edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.product + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditInventory(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventory(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
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
	$('#successCount').html("" + successCount);
	$("#errorCount").css("color", "black");
	if(errorData.length>0){
		$("#errorCount").css("color", "red");
	}
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#process-data').prop('disabled',false);
	$("#download-errors").prop('disabled', true);
	$('#inventoryFileName').html(fileName);
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
	$('#upload-inventory-modal').modal('toggle');
}

function displayInventory(data){
	$("#inventory-edit-form input[name=product]").val(data.product);	
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);	
	$("#inventory-edit-form input[name=id]").val(data.id);
	$("#update-inventory").prop('disabled', true);	
	inventoryQuantity= data.quantity
	$('#edit-inventory-modal').modal('toggle');
}


function displayAddData(){
	
	$("#add-inventory").prop('disabled', true);
	$( '#inventory-add-form' ).each(function(){
   			 this.reset();
				});
	$('#add-inventory-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#add-data').click(displayAddData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getInventoryList);


$('#inventory-edit-form input[name=quantity] ').on("input",function(){
  
    if ($('#inventory-edit-form input[name=quantity] ').val().length  >   0 && $('#inventory-edit-form input[name=quantity] ').val()!=inventoryQuantity ) {
        $("#update-inventory").prop("disabled", false);
    }
    else {
        $("#update-inventory").prop("disabled", true);
    }
  
});

$('#inventory-add-form input[name=product],#inventory-add-form input[name=quantity] ').on("input",function(){
  
    if ($('#inventory-add-form input[name=product]').val().length   >   0   &&
        $('#inventory-add-form input[name=quantity] ').val().length  >   0  ) {
        $("#add-inventory").prop("disabled", false);
    }
    else {
        $("#add-inventory").prop("disabled", true);
    }
  
});

$('#inventory-add-form input[name=product]').on("input",function(){
	var value=$('#inventory-add-form input[name=product]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-productId").text("");
 		
 	}
 	else{
 		$("#check-productId").text("Please enter Integer value");
 		$("#add-inventory").prop("disabled", true);
 	}
  
});

$('#inventory-add-form input[name=quantity]').on("input",function(){
	var value=$('#inventory-add-form input[name=quantity]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-quantity").text("");
 		
 	}
 	else{
 		$("#check-quantity").text("Please enter Integer value");
 		$("#add-inventory").prop("disabled", true);
 	}
  
});

$('#inventory-edit-form input[name=quantity]').on("input",function(){
	var value=$('#inventory-edit-form input[name=quantity]').val();
  if(( value.length ==   0) || (Math.floor(value) == (value) && $.isNumeric(value))) 
 	{
 		$("#check-edit-quantity").text("");
 		
 	}
 	else{
 		$("#check-edit-quantity").text("Please enter Integer value");
 		$("#update-inventory").prop("disabled", true);
 	}
  
});


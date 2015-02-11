<#import "layout.ftl" as layout>
<@layout.sb_admin>
<div class="row">
    <div class="col-lg-12">
    	<h2>OpenTOSCA Server-Details</h2>
		<form action="${basePath}/updateopentoscaserver" method="POST" class="form-horizontal">
		<fieldset id="inputFieldSet" disabled>
			<div class="form-group">
	  			<input type="hidden" name="openToscaServerId" value="${openToscaServer.id}">
	  			<label for="nameField">Name:</label>
	  			<input type="text" class="form-control" id="nameField" name="openToscaServerName" value="${openToscaServer.name}">
	  
	  			<label for="urlField">ContainerAPI URL:</label>
	  			<input type="url" class="disabled form-control" id="urlField" name="address" value="${openToscaServer.address}">
			</div>
		</fieldset>
			<div class="form-group">
		    	<div class="text-right col-sm-12">
		      		<button class="btn btn-default" id="unlockButton">
		      		<span class="glyphicon glyphicon-plus"></span> 
		      		&nbsp;Unlock</button>
		      		<button id="submitBtn" type="submit" class="btn btn-success disabled">
		      		<span class="glyphicon glyphicon-plus"></span> 
		      		&nbsp;Save OpenTOSCA-Server MetaData </button>
				</div>
			</div>
	</form>
	</div>
</div>
<div class="row">
<div class="col-lg-12">
	<h2>OpenTOSCA Deployments</h2>
		<table id="ciList" class="table table-striped table-bordered" border="1">
			<thead>
				<tr>
					<th>ServiceInstance-ID</th>
					<th>CSAR-ID</th>
					<th>ServiceTemplateID</th>
				</tr>
			</thead>
			<tbody>
				<#list liveEntries as live>
					<tr>
						<td>${live.serviceInstanceID}</td>
						<td>${live.csarID}</td>
						<td>${live.serviceTemplateID}</td>
					</tr>
				</#list>
			</tbody>
		</table>
</div>
</div>

<script>
	window.onload = function() {
		$(document).ready(function() {
			$('#unlockButton').click(
				function(e){
					e.preventDefault();
					$('#inputFieldSet').removeAttr('disabled','disabled');
					$('#submitBtn').removeClass('disabled');
					$(this).hide();
				}
			);
	    	$('#csarFileList').dataTable();
		});
	}
</script>

</@layout.sb_admin>

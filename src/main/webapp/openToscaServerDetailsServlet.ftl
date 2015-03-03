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
		    		<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
    					<span class="glyphicon glyphicon-remove"></span>
    					Delete
					</button>
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
        
        <#if openToscaMessage??>
            <div class="alert alert-warning" role="alert">${openToscaMessage}</div>
		<#elseif liveEntries?size gt 0>
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
		<#else>
	       <div class="alert alert-warning" role="alert">No ServiceInstances found</div>
		</#if>
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

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteModalLabel">Delete OpenTosca server</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete OpenTosca server <strong>${openToscaServer.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deleteopentoscaserver/${openToscaServer.id}">
	                <button type="button" class="btn btn-danger pull-right" style="margin: 3px;">
	                	Delete
	                </button>
                </a>
                <button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="margin: 3px;">Cancel</button>
                <br />
                <br />
            </div>
        </div>
    </div>
</div>
</@layout.sb_admin>
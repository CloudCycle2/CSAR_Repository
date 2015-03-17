<#import "layout.ftl" as layout>
<@layout.sb_admin>
<div class="row">
    <div class="col-lg-12">

		<h2>CsarFile-Details</h2>
			<ul>
				<li><strong>CSAR: </strong>${csar.name} (<a href="${basePath}/csar/${csar.id}">Details</a>)</li>
				<li><strong>Version: </strong>${csarFile.version}</li>
				<li><strong>Filename: </strong>${csarFile.name}</li>
				<li><strong>Uploaded at: </strong>${csarFile.uploadDate}</li>
				<li><strong>Filesize: </strong>${hashedFile.size}</li>
				<li><strong>Hash: </strong>${hashedFile.hash}</li>
			</ul>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<h2>Delete CSAR File</h2>
		<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
    		<span class="glyphicon glyphicon-remove"></span>
    		Delete
		</button>
	</div>
</div>

<div class="row">
    <div class="col-lg-12">
        <h2>OpenTOSCA Servers where this CSAR-File is deployed</h2>
        <table id="otList" class="table table-striped table-bordered" border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>OpenTOSCA</th>
                    <th>location</th>
                    <th>undeploy</th>
                </tr>
            </thead>
            <tbody>
                <#list opentoscaDeployedTo as opentoscaServer>
                    <tr>
                        <td>${opentoscaServer.openToscaServer.id}</td>
                        <td>${opentoscaServer.openToscaServer.name}</td>
                        <td>${opentoscaServer.openToscaServer.address}</td>
                        <td><a href="${basePath}/opentoscaserver/${opentoscaServer.getOpenToscaServer().getId()}">${opentoscaServer.openToscaServer.name}</a></td>
                        <td>${opentoscaServer.location}</td>
                        <td><a href="${basePath}/undeploycsarfile?opentoscaId=${opentoscaServer.openToscaServer.id}&csarfileId=${opentoscaServer.csarFile.id}"><i class="fa fa-eraser fa-lg"></i></a></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>   

<div class="row">
	<div class="col-lg-12">
		<h2>Cloud Instances</h2>
		<table id="ciList" class="table table-striped table-bordered" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Address</th>
					<th>OpenTOSCA</th>
				</tr>
			</thead>
			<tbody>
				<#list cloudInstances as ci>
					<tr>
						<td>${ci.id}</td>
						<td>${ci.name}</td>
						<td>${ci.address}</td>
						<td><a href="${basePath}/#">${ci.openToscaServer.name}</a></td>
					</tr>
				</#list>
			</tbody>
		</table>

		<script>
			window.onload=function(){
				$(document).ready(function() {
			    	$('#otList').dataTable();
			    	$('#ciList').dataTable();
				});
			}
		</script>
	</div>
</div>	 

<h2>Deploy on OpenTOSCA Instance</h2>
<#if allOpentoscaServers?size gt 0>
	<form action="${basePath}/deploycsarfile" method="POST" class="form-horizontal">
	<div class="form-group">
	  <label for="sel1">Select OpenTOSCA Instance to deploy:</label>
	  <input type="hidden" name="csarfileId" value="${csarFile.id}">
	  <select class="form-control" id="opentoscaId" name="opentoscaId">
		<#list allOpentoscaServers as otServer>
	    <option value="${otServer.id}">${otServer.name} | ${otServer.address}</option>
		</#list>
	  </select>
	</div>
	<div class="form-group">
		    <div class="text-right col-sm-12">
		      <button type="submit" class="btn btn-success">
		      	<span class="glyphicon glyphicon-plus"></span> 
		      	&nbsp;Deploy to OpenTOSCA Server</button>
			</div>
	</div>
	</form>
<#else>
	<div class="alert alert-warning" role="alert">No OpenTOSCA Instances created</div>
</#if>

<h2>Export to winery</h2>
<#if wineryServers?size gt 0>
	<form action="${basePath}/exporttowinery" method="POST" class="form-horizontal">
	<div class="form-group">
		<input type="hidden" name="csarfileId" value="${csarFile.id}" />
	  	<label for="inputWineryId">Select Winery server:</label>
	  	<select class="form-control" id="inputWineryId" name="wineryId">
			<#list wineryServers as ws>
		    	<option value="${ws.id}">${ws.name} | ${ws.address}</option>
			</#list>
	  	</select>
	</div>
	<div class="form-group">
		    <div class="text-right col-sm-12">
		      <button type="submit" class="btn btn-success">
		      	<span class="glyphicon glyphicon-share"></span> 
		      	Export to Winery</button>
			</div>
	</div>
	</form>
<#else>
	<div class="alert alert-warning" role="alert">No Winery servers created</div>
</#if>


<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteModalLabel">Delete version of CSAR</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete <strong>Version ${csarFile.version} of ${csar.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deletecsarfile?csarfileid=${csarFile.id}">
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
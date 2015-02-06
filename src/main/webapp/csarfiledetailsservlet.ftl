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
        <h2>OpenTOSCA Servers where this CSAR-File is deployed</h2>
        <table id="otList" class="table table-striped table-bordered" border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>OpenTOSCA</th>
                    <th>location</th>
                </tr>
            </thead>
            <tbody>
                <#list opentoscaDeployedTo as opentoscaServer>
                    <tr>
                        <td>${opentoscaServer.openToscaServer.id}</td>
                        <td>${opentoscaServer.openToscaServer.name}</td>
                        <td>${opentoscaServer.openToscaServer.address}</td>
                        <td><a href="${basePath}/#">${opentoscaServer.openToscaServer.name}</a></td>
                        <td>${opentoscaServer.location}</td>
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
</@layout.sb_admin>
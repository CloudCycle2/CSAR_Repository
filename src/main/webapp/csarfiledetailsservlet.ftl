<#import "layout.ftl" as layout>
<@layout.sb_admin>
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
		<table id="ciList" class="table table-striped table-bordered" border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>URL</th>
				</tr>
			</thead>
			<tbody>
				<#list cloudInstances as ci>
					<tr>
						<td>${ci.id}</td>
						<td>${ci.name}</td>
						<td>${ci.address}</td>
					</tr>
				</#list>
			</tbody>
		</table>

		<script>
			window.onload=function(){
				$(document).ready(function() {
			    	$('#ciList').dataTable();
				});
			}
		</script>
	</div>
</div>	 
</@layout.sb_admin>
<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createcsar" method="POST" class="form-horizontal">
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverName">Name</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="csarName" name="csarName">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-left col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Create new CSAR</button>
		</div>
	</div>
</form>

<table id="csarList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>Name</th>
			<th>Count</th>
			<th>Last Version</th>
		</tr>
	</thead>
	<tbody>
<#list csars as csar>
	<tr>
		<td><a href="${basePath}/csar/${csar.id}">${csar.name}</a></td>
		<td style="text-align: center;">${csar.csarFiles?size}</td>
		<td style="text-align: center;">
			<#if csar.csarFiles?has_content>
				${csar.csarFiles?sort_by("version")?last.version}
			<#else>
				-
			</#if>
		</td>
	</tr>
</#list>
</tbody>
</table>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarList').dataTable();
		});
	}
</script>

</@layout.sb_admin>
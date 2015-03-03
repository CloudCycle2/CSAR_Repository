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

<#if wineryList?size gt 0>
	<div class="btn-group">
		<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
			Create new CSAR from Winery <span class="caret"></span>
		</button>
		<ul class="dropdown-menu" role="menu">
			<#list wineryList as winery>
				<li>
					<a href="${basePath}/wineryserver/${winery.id}#serviceTemplates">${winery.name}</a>
				</li>
			</#list>
		</ul>
	</div>
	<br />
	<br />
	<br />
</#if>
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
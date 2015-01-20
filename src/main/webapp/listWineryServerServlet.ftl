<#import "layout.ftl" as layout>
<@layout.sb_admin>

<h3>Create new winery server</h3>
<form action="${basePath}/createwinery" method="POST" class="form-horizontal">
  <div class="form-group">
    <label for="inputWineryServerName" class="col-sm-1 control-label">Name</label>
    <div class="col-sm-11">
      <input name="wineryServerName" type="text" class="form-control" id="inputWineryServerName" placeholder="Enter name for winery server">
    </div>
  </div>
  <div class="form-group">
    <label for="inputWineryServerUrl" class="col-sm-1 control-label">URL</label>
    <div class="col-sm-11">
      <input name="wineryServerUrl" type="text" class="form-control" id="inputWineryServerUrl" placeholder="Enter URL of winery server">
    </div>
  </div>
  <div class="form-group">
    <div class="text-right col-sm-12">
      <button type="submit" class="btn btn-success">
      	<span class="glyphicon glyphicon-plus"></span>
      	Create new winery server
      </button>
    </div>
  </div>
</form>

<h3>Existing winery servers</h3>
<table id="wineryServerList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>Winery Server Name</th>
			<th>URL</th>
		</tr>
	</thead>
	<tbody>
<#list wineryServers as winery>
	<tr>
		<td><a href="${basePath}/wineryserver/${winery.id}">${winery.name}</a></td>
		<td>${winery.address}</td>
	</tr>
</#list>
</tbody>
</table>

<script>
${r"$(document).ready(function() {
    $('#wineryServerList').dataTable();
} );"}
</script>

</@layout.sb_admin>
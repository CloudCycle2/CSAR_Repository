<#import "layout.ftl" as layout>
<@layout.sb_admin>

<h2>Import Servicetemplate from ${winery.getName()}</h2>
<#if servicetemplates?size gt 0>
	<hr />
	<#list servicetemplates as st>
		<div class="pull-left">
			<strong>${st.name}</strong> <br />
			<strong>Id:</strong> ${st.id} <br />
			<strong>Namespace:</strong> ${st.namespace}
		</div>
		<div class="pull-right">
			<form action="${basePath}/newversionfromwinery" method="post">
				<input type="hidden" name="wineryId" value="${winery.id}" />
				<input type="hidden" name="csarId" value="${csar.id}" />
				<input type="hidden" name="servicetemplate" value="${st.getWineryAddress()}" />
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-import"></span> 
					Import into CSAR ${csar.name}
				</button>
			</form>
		</div>
		<div class="clearfix"></div>
		<hr />
</#list>
<#else>
	<div class="alert alert-warning" role="alert">No servicetemplates found</div>
</#if>
</@layout.sb_admin>
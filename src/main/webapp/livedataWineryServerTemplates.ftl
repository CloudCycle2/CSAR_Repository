<#if errorMessages??>
    <div class="alert alert-warning" role="alert">${errorMessages}</div>
<#elseif servicetemplates?has_content>
	<#list servicetemplates as st>
		<div class="pull-left">
			<strong>${st.name}</strong> <br />
			<strong>Id:</strong> ${st.id} <br />
			<strong>Namespace:</strong> ${st.namespace}
		</div>
		<div class="pull-right">
			<form action="${basePath}/importcsarfromwinery" method="post">
				<input type="hidden" name="wineryId" value="${wineryServer.id}" />
				<input type="hidden" name="servicetemplate" value="${st.getWineryAddress()}" />
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-import"></span> 
					Import as new CSAR
				</button>
			</form>
		</div>
		<div class="clearfix"></div>
		<hr />
	</#list>
<#else>
	<div class="alert alert-warning" role="alert">No servicetemplates found</div>
</#if>

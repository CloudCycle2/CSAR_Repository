<#if errorMessages??>
    <div class="alert alert-warning" role="alert">${errorMessages}</div>
<#elseif deployedCsars?has_content>
<table id="ciList" class="table table-striped table-bordered" border="1">
    <thead>
        <tr>
            <th>Name</th>
            <th>Build-Plans</th>
        </tr>
    </thead>
    <tbody>
        <#list deployedCsars as deployedCsar>
            <tr>
                <td>${deployedCsar.title}</td>
                <td>
                <#list PlanInvocationHelper.generateLinkToBuildPlan(openToscaServer, deployedCsar.title) as link>
                  <a href="${link.href}">${link.text}</a>
                </#list>
                </td>
            </tr>
        </#list>
     </tbody>
</table>
<#else>
   <div class="alert alert-warning" role="alert">No deployed CSARs found</div>
</#if>
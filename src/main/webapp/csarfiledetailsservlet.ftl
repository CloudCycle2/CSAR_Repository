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
</@layout.sb_admin>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<sj:head jqueryui="true" jquerytheme="cupertino"/>
	<sb:head/>
	<style type="text/css">
		.table-head:link 
		{
			text-decoration: none;
		}
	</style>
</head>
<body>
	<s:div cssClass="container">
		<s:div cssClass="row">
			<s:a action="showTable" class="btn btn-info"><span class="glyphicon glyphicon-home"></span></s:a>
			<s:a href="#" data-toggle="modal" data-target="#mySearchModal" class="btn btn-success">Search <span class="glyphicon glyphicon-search"></span></s:a>
			<s:a href="#" class="btn btn-default" onclick="printList()">Print <span class="glyphicon glyphicon-print"></span></s:a>
		</s:div>
		<s:div cssClass="row table-responsive">
			<table class="table table-hover table-striped">
				<thead>
					<s:set name="glyph" value="''"></s:set>
					<s:if test="%{order == 'Asc'}">
						<s:set name="glyph" value="'glyphicon glyphicon-chevron-up'"/>
					</s:if>
					<s:elseif test="%{order == 'Des'}">
						<s:set name="glyph" value="'glyphicon glyphicon-chevron-down'"/>
					</s:elseif>
					
					<tr id="eTableHead">
						<th><s:a href="#" id="eNumber" class="table-head" onclick="sortBy('eNumber','%{sortByField}','%{order}')">Employee No. <span></span></s:a></th>
						<th><s:a href="#" id="fName" class="table-head" onclick="sortBy('fName','%{sortByField}','%{order}')">Full Name <span></span></s:a></th>
						<th><s:a href="#" id="loc" class="table-head" onclick="sortBy('loc','%{sortByField}','%{order}')">Location <span></span></s:a></th>
						<th><s:a href="#" id="man" class="table-head" onclick="sortBy('man','%{sortByField}','%{order}')">Manager <span></span></s:a></th>
						<th><s:a href="#" id="sDate" class="table-head" onclick="sortBy('sDate','%{sortByField}','%{order}')">Start Date <span></span></s:a></th>
						<th><s:a href="#" id="jTitle" class="table-head" onclick="sortBy('jTitle','%{sortByField}','%{order}')">Job title <span></span></s:a></th>
						<th><s:a href="#" id="pNumber" class="table-head" onclick="sortBy('pNumber','%{sortByField}','%{order}')">Phone Number <span></span></s:a></th>
						<th><s:a href="#" id="eml" class="table-head" onclick="sortBy('eml','%{sortByField}','%{order}')">E-Mail <span></span></s:a></th>
						<th><s:a href="#" id="sal" class="table-head" onclick="sortBy('sal','%{sortByField}','%{order}')">Salary <span></span></s:a></th>
						<th>Action</th>
					</tr>
					<tr>
						<td><s:a action="createEmployee" cssClass="btn btn-primary">Create</s:a></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</thead>
				<tbody id="eTableBody">
					
					<s:iterator value="arrayEmployee" var="e">
						<tr>
							<td>
								<s:property value="employeeNumber"/>
							</td>
							<td>
								<s:property value="fullName"/>
							</td>
							<td>
								<s:property value="location"/>
							</td>
							<td>
								<s:property value="manager"/>
							</td>
							<td>
								<s:property value="simpleStartDate"/>
							</td>
							<td>
								<s:property value="jobTitle"/>
							</td>
							<td>
								<s:property value="phoneNumber"/>
							</td>
							<td>
								<s:property value="email"/>
							</td>
							<td>
								<s:property value="salary"/>
							</td>
							<td>
								<s:a class="btn btn-sm btn-info" href="#" data-toggle="modal" data-target="#myModal" onclick="showListOfSubordinates('%{subordinatesString}')">Detail</s:a>
								<s:a cssClass="btn btn-sm btn-warning" action="editEmployee?employeeNumber=%{employeeNumber}&fullName=%{fullName}&location=%{location}&manager=%{manager}&startDate=%{startDate}&jobTitle=%{jobTitle}&phoneNumber=%{phoneNumber}&email=%{email}&salary=%{salary}">Edit</s:a>
								<s:a cssClass="btn btn-sm btn-danger"  action="deleteEmployee?employeeNumber=%{employeeNumber}">Delete</s:a>
							</td>
						</tr>
					</s:iterator>
			
				</tbody>
				
			</table>
		</s:div>
	</s:div>
	<s:a href="#" id="btnOrder" hidden="true" onclick="toggleIcon('%{sortByField}','%{glyph}')"></s:a>
</body>
<footer>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4>Subordinates</h4>
				</div>
				<div class="modal-body" id="modalBody">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="mySearchModal" tabindex="-1" role="dialog" aria-labelledby="myModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4>Search By</h4>
				</div>
				<div class="modal-body">
					<s:form id="mySearchForm" action="searchEmployees" theme="bootstrap" cssClass="well form-vertical">
						<fieldset>
								<s:div cssClass="form-group">
									<label >Choose field:</label>
									<select class="form-control" name="employeeFields" id="employeeFields">
										<option>Employee Number</option> 
										<option>Full Name</option> 
										<option>Job Title</option> 
										<option>Location</option> 
										<option>Salary</option> 
									</select>
								</s:div>
								<s:div cssClass="form-group" id="searchInputField">
									<label>Employee Number</label>
									<input class="form-control" name="employeeNumber" id="searchInput" type="number">
								</s:div>
						</fieldset>
					</s:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" onclick="$('#mySearchForm').submit()">Search <span class="glyphicon glyphicon-search"></span></button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close <span class="glyphicon glyphicon-off"></button>
				</div>
			</div>
		</div>
	</div>
	<script>
		
		$("body").ready(function(){
			$("#btnOrder").click();
		});
		
		function toggleIcon(field,glyph)
		{
			$("#"+field.substr(0,field.length-3)+" span").addClass(glyph);
		}
		
		function sortBy(field,f,order)
		{
			if(field != f.substr(0,f.length-3)) return window.location.href="/mptest/showTable?sortByField="+field+"Asc&order=Asc";	
			if(order == "") return window.location.href="/mptest/showTable?sortByField="+field+"Asc&order=Asc";
			else if(order == "Asc") return window.location.href="/mptest/showTable?sortByField="+field+"Des&order=Des";
			else return window.location.href="/mptest/showTable?sortByField="+field+"Asc&order=Asc"
		}
		
		function showListOfSubordinates(result)
		{
			$("#modalBody").html(result);
		}
		
		function printList() {
			var oldBodyContent = $("body").html();
			
			var newBodyContent = "";
			$.getJSON("getemployees",function(key,val){
				$.each(key,function(key,val){
					newBodyContent += "<p>Employee Number: "+val["employeeNumber"]+", Full Name: "+val["fullName"]+", Job Title: "+val["jobTitle"]+", Location: "+val["location"]+", Manager: "+val["manager"]+", Start Date: "+val["simpleStartDate"]+", Phone Number: "+val["phoneNumber"]+", Email: "+val["email"]+", Salary: "+val["salary"]+", Subordinates: "+val["subordinates"]+"</p>";
				});
				$("body").html(newBodyContent);
				window.print();
				$("body").html(oldBodyContent);
			});

		}
		
		$("#employeeFields").change(function(){
			var field = $("#employeeFields").val();
			switch(field)
			{
			case "Employee Number":
				$("#searchInputField").html("<label>"+field+"</label><input class='form-control' name='employeeNumber' id='searchInput' type='number'>");
				break;
			case "Full Name":
				$("#searchInputField").html("<label>"+field+"</label><input class='form-control' name='fullName' id='searchInput' type='text'>");
				break;
			case "Job Title":
				$("#searchInputField").html("<label>"+field+"</label><input class='form-control' name='jobTitle' id='searchInput' type='text'>");
				break;
			case "Location":
				$("#searchInputField").html("<label>"+field+"</label><input class='form-control' name='location' id='searchInput' type='text'>");
				break;
			case "Salary":
				$("#searchInputField").html("<label>"+field+"</label><div class='row'><div class='col-md-4'><select class='form-control' name='queryType' id='typeFields'><option value='<'>less than</option><option value='='>equals to</option><option value='>'>greater than</option></select></div><div class='col-md-8'><input class='form-control' name='salary' id='searchInput' type='number'></div>");
				break;
			}
		});

	</script>
</footer>

</html>
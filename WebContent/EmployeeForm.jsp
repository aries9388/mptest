<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <sj:head jqueryui="true"/>
	<sb:head/>
</head>
<body>
	<s:set name="eid" value="employeeNumber"/>
	<s:if test="%{#eid == 0}">
		<s:set name="act" value="'addEmployee'"/>
		<s:set name="isDisabled" value="'false'"/>
		<s:set name="initid" value="''"/>
	</s:if>
	<s:else>
		<s:set name="act" value="'updateEmployee'"/>
		<s:set name="isDisabled" value="'true'"/>
		<s:set name="initid" value="employeeNumber"/>
	</s:else>
	
	<s:div cssClass="container">
		<s:div cssClass="row">
			<h1>Employee Form</h1>
			<s:form action="%{act}" theme="bootstrap" cssClass="well form-vertical">
				<fieldset>
					<s:div cssClass="row">
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Employee Number" tooltip="Please enter the employee number" key="employeeNumber" value="%{initid}" />
						</s:div>
						<s:div cssClass="form-group col-md-4">
							<sj:datepicker
	                                id="datepicker"
	                                label="Start Date"
	                                parentTheme="bootstrap"
	                                tooltip="Please choose a date"
	                                cssClass="form-control"
	                                showOn="focus"
	                                inputAppendIcon="calendar"
	                                key="startDate"
	                    	/>
						</s:div>
					</s:div>
					
					<s:div cssClass="row">
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Full Name" tooltip="Please enter the full name" key="fullName" />
						</s:div>
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Job Title" tooltip="Please enter the job title" key="jobTitle" />
						</s:div>
					</s:div>
					
					<s:div cssClass="row">
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Location" tooltip="Please enter the location" key="location"/>
						</s:div>
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Phone Number" tooltip="Please enter the phone number" type="tel" inputAppendIcon="phone" key="phoneNumber" />
						</s:div>
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="E-mail" tooltip="Please enter the email" type="email" inputAppendIcon="envelope" key="email" />
						</s:div>
					</s:div>
					
					<s:div cssClass="row">
						<s:div cssClass="form-group col-md-4">
							<sj:autocompleter href="getmanagers" cssClass="form-control" label="Manager" tooltip="Please enter the manager's name" key="manager" id="jsonResult" delay="50" loadMinimumCount="2"></sj:autocompleter>
						</s:div>
						<s:div cssClass="form-group col-md-4">
							<s:textfield label="Salary" tooltip="Please enter the salary" inputAppendIcon="usd" key="salary" />
						</s:div>
					</s:div>
					
					<s:div cssClass="row">
						<s:div cssClass="form-group col-md-4">
							<s:submit value="Save" cssClass="btn btn-primary"/>
							<s:a action="showTable" cssClass="btn btn-default">Back</s:a>
						</s:div>
					</s:div>
					
					
				</fieldset>
				
			</s:form>
		</s:div>
		
	</s:div>
	
</body>
</html>
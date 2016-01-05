<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
     
<html>
<head>
    <title>Add New User Form</title>
</head>
<body>
<f:view>		  
    <h:form>
    	<p>
			<h:messages globalOnly="true" showDetail="true"/>		
        </p>
	    
        <h:panelGrid border="0" columns="3">        
            <h:outputText value="ID"></h:outputText>            
            <h:inputText id="User_ID" value="#{userBean.id}" required="true" requiredMessage="Id is required">            	            	
                <f:validateLongRange minimum="1" maximum="50"/>
            </h:inputText>
            <h:message id="error1" for="User_ID" style="color:red"/>
            
            <h:outputText value="Name"></h:outputText>
            <h:inputText id="name" value="#{userBean.name}" required="true">
            	<f:validateLength minimum="3" maximum="15"/>
            </h:inputText>
            <h:message id="error2" for="name" style="color:red"/>
                     
            <h:outputText value="Email"></h:outputText>   
            <h:inputText id="Email" value="#{userBean.email}" required="true">
				<f:validator validatorId="emailValidator" />
			</h:inputText>       
			<h:message id="error3" for="Email" style="color:red"/>                                     
        </h:panelGrid>
        <h:commandButton id="submitButton" action="#{userBean.addUser}" value="Add Customer" ></h:commandButton>                      
    </h:form>
</f:view>
</body>
</html>

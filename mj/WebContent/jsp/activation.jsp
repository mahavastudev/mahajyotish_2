<h:head>
   <%-- <ui:fragment rendered="#{activation.valid}">--%>
        <meta http-equiv="refresh" content="3;url=index.jsp" />
<%--    </ui:fragment>--%>
</h:head>
<h:body>
   <%-- <h:panelGroup layout="block" rendered="#{activation.valid}">--%>
    <h:panelGroup layout="block">
        <p>Your account is successfully activated!</p>
        <p>You will in 3 seconds be redirected to <h:link outcome="home">home page</h:link></p>
    </h:panelGroup>
    <%--<h:panelGroup layout="block" rendered="#{!activation.valid}">
    <h:panelGroup layout="block">
        <p>Activation failed! Please enter your email address to try once again.</p> 
    </h:panelGroup>--%>
</h:body>

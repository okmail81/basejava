<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
    <h2>${type.title}</h2>
    <c:choose>
        <c:when test="${type=='PERSONAL'|| type=='OBJECTIVE'}">
            <label>
                <%=section%>
            </label>
        </c:when>
        <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
            <label>
                <%=section.toString()%>
            </label>
        </c:when>
        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
            <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>"
                       varStatus="counter">
                <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                <dd>
                    <label>
                        <h3><%=organization.getHomePage().getName()%>
                        </h3>
                    </label>
                </dd>
                <br>
                Веб сайт:
                <dd>
                    <label>
                        <%=organization.getHomePage().getUrl()%>
                    </label>
                </dd>
                <br>
                <c:forEach var="position" items="<%=organization.getPositions()%>">
                    <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                    <br>
                    с
                    <label>
                        <%=position.getStartDate()%>
                    </label>
                    по
                    <label>
                        <%=(position.getEndDate() == DateUtil.NOW) ? "настоящее время":position.getEndDate()%>
                        <br>
                    </label>
                    <label>
                        <%=position.getTitle()%>
                        <br>
                    </label>
                    <c:if test="${type=='EXPERIENCE'}">
                        <label>
                            <%=position.getDescription()%>
                            <br>
                        </label>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </c:when>
    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
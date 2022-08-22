<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><label>
                <input type="text" name="fullName" size=50 value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type=='PERSONAL'|| type=='OBJECTIVE'}">
                    <label>
                        <textarea name='${type.name()}' cols=100 rows=1><%=section%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <label>
                        <textarea name='${type.name()}' cols=100 rows=3><%=section.toString()%></textarea>
                    </label>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>"
                               varStatus="counter">
                        <jsp:useBean id="organization" type="com.urise.webapp.model.Organization"/>
                        <br>
                        Организация:
                        <dd>
                            <label>
                                <input type="text" name="${type.name()}" size=75
                                       value="${organization.homePage.name}">
                            </label>
                        </dd>
                        <br>
                        Веб сайт:
                        <dd>
                            <label>
                                <input type="text" name="${type.name()}homePageNameUrl" size=75
                                       value="${organization.homePage.url}">
                            </label>
                        </dd>
                        <h4>Занимаемые позиции: </h4>
                        <c:forEach var="position" items="<%=organization.getPositions()%>">
                            <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                            с
                            <label>
                                <input type="date" name="${type.name()}startDate${counter.index}" size=25
                                       value="${position.startDate}">
                            </label>
                            по
                            <label>
                                <input type="date" name="${type.name()}endDate${counter.index}" size=25
                                       value="${(position.endDate == DateUtil.NOW) ? null:position.endDate}">
                            </label>
                            <label>
                                <textarea name='${type.name()}positionTitle${counter.index}' cols=100
                                          rows=2><%=position.getTitle()%></textarea>
                                <br>
                            </label>
                            <c:if test="${type.name()=='EXPERIENCE'}">
                                <label>
                                    <textarea name='${type.name()}positionDescription${counter.index}' cols=100
                                              rows=2><%=position.getDescription()%></textarea>
                                    <br>
                                </label>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button name="submit" type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
package com.demo.Spring.Boot.Rest.API;

import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import javax.swing.*;

public class PersonServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String name = requestUrl.substring("/people/".length());

        Person person = DataStore.getInstance().getPerson(name);

        if(person != null){
            String json = "{\n";
            json += "\"name\": " + JSONObject.quote(person.getName()) + ",\n";
            json += "\"about\": " + JSONObject.quote(person.getAbout()) + ",\n";
            json += "\"birthYear\": " + person.getBirthYear() + "\n";
            json += "}";
            response.getOutputStream().println(json);
        }
        else{
            //That person wasn't found, so return an empty JSON object. We could also return an error.
            response.getOutputStream().println("{}");
        }
    }



    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter("name");
        String about = request.getParameter("about");
        int birthYear = Integer.parseInt(request.getParameter("birthYear"));
        String password= request.getParameter("password");

        DataStore.getInstance().putPerson(new Person(name, about, birthYear, password));
    }
    public void doPost1(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String name = request.getParameter("name");
        String about = request.getParameter("about");
        String password = request.getParameter("password");
        int birthYear = Integer.parseInt(request.getParameter("birthYear"));

        Person personToEdit = DataStore.getInstance().getPerson(name);

        if(personToEdit != null && personToEdit.getPassword().equals(password)){
            DataStore.getInstance().putPerson(new Person(name, about, birthYear, password));
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    public void doPost2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ')+1);
        String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        String username = decodedAuth.substring(0, decodedAuth.indexOf(':'));
        String password = decodedAuth.substring(decodedAuth.indexOf(':')+1);

        String nameToEdit = request.getParameter("name");
        String about = request.getParameter("about");
        int birthYear = Integer.parseInt(request.getParameter("birthYear"));

        Person personToEdit = DataStore.getInstance().getPerson(nameToEdit);
        Person loggedInPerson = DataStore.getInstance().getPerson(username);

        //make sure user is in our data
        if(personToEdit == null || loggedInPerson == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //don't let users edit other users
        if(!nameToEdit.equals(loggedInPerson.getName())){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //make sure password is valid
        //use hashed passwords in real life!
        if(!password.equalsIgnoreCase(loggedInPerson.getPassword())){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //if we made it here, everything is okay, save the user
        DataStore.getInstance().putPerson(new Person(nameToEdit, about, birthYear, password));
    }

    //new

    public void doGet2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String name = requestUrl.substring("/people/".length());

        Person person = DataStore.getInstance().getPerson(name);

        if(person != null){
            String json = "{\n";
            json += "\"name\": " + JSONObject.quote(person.getName()) + ",\n";
            json += "\"about\": " + JSONObject.quote(person.getAbout()) + ",\n";
            json += "\"birthYear\": " + person.getBirthYear() + "\n";
            json += "}";
            response.getOutputStream().println(json);
        }
        else{
            //That person wasn't found, so return an empty JSON object. We could also return an error.
            response.getOutputStream().println("{}");
        }
    }

    public void doPost3(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String authHeader = request.getHeader("authorization");
        String encodedToken = authHeader.substring(authHeader.indexOf(' ')+1);
        String decodedToken = new String(Base64.getDecoder().decode(encodedToken));
        String username = TokenStore.getInstance().getUsername(decodedToken);

        //token is invalid or expired
        if(username == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String nameToEdit = request.getParameter("name");
        String about = request.getParameter("about");
        int birthYear = Integer.parseInt(request.getParameter("birthYear"));

        Person loggedInPerson = DataStore.getInstance().getPerson(username);

        //don't let users edit other users
        if(!nameToEdit.equals(loggedInPerson.getName())){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        loggedInPerson.setAbout(about);
        loggedInPerson.setBirthYear(birthYear);

        //if we made it here, everything is okay, save the user
        DataStore.getInstance().putPerson(loggedInPerson);
    }
}

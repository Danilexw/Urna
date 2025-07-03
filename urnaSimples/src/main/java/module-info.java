module urnaSimples {
	exports br.com.poo.urna.controller;
	exports br.com.poo.urna.app;
	exports br.com.poo.urna.dao;
	exports br.com.poo.urna.model;
	
	opens br.com.poo.urna.app to javafx.fxml;
    opens br.com.poo.urna.controller to javafx.fxml;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.mongodb.bson;
	requires org.mongodb.driver.core;
	requires org.mongodb.driver.sync.client;
}
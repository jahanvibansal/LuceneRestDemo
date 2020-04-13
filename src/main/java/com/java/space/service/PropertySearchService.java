package com.java.space.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;

import com.java.space.dto.Property.Category;
import com.java.space.dto.Property.Purpose;
import com.java.space.dto.Property.Timings;
import com.java.space.dto.Property.Type;
import com.java.space.dto.PropertyDetails.Availability;

public interface PropertySearchService {

	//Search properties by combination of type, purpose and city. Each can expect 2 typos
	List<Document> searchByTypePurposeAndCity(Directory index, Type type, Purpose purpose, String city)
			throws IOException, ParseException;

	//Search properties by combination of type, purpose and city. Each can expect 2 typos
	List<Document> searchByTimings(Directory index, Timings timing) throws IOException, ParseException;

	List<Document> searchBySpaceNameOrTitle(Directory index, String text) throws IOException, ParseException;

	List<Document>  searchByAvailability(Directory index, Availability availability) throws IOException, ParseException;

	List<Document>  searchByPopularity(Directory index, int popularity) throws IOException, ParseException;

	List<Document>  searchByCategory(Directory index, Category category) throws IOException, ParseException;

}
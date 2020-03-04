package com.bridgelabz.fundoonotes.repository.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.IElasticSearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Repository of elastic search which implements
 * {@link IElasticSearchRepository} which provide the functionality of create ,
 * update, delete, search operation.
 * 
 * @author Durgasankar Mishra
 * @created 2020-03-04
 * @version 1.0
 * @see {@link ElasticSearchConfig} for configuration
 * @see {@link ObjectMapper} for reading and writing JSON
 */
@Repository
public class ElasticSearchRepositoryImpl implements IElasticSearchRepository {

	@Autowired
	private ElasticSearchConfig config;
	@Autowired
	private ObjectMapper objectmapper;

	private static final String INDEX = "fundoo";
	private static final String TYPE = "note_details";

	@Override
	public void createNote(Note note) {
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId()))
				.source(objectmapper.convertValue(note, Map.class));
		// Index response
		try {
			config.client().index(indexrequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String updateNote(Note note) {
//		System.out.println(note.getNoteId());
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteId()))
				.doc(objectmapper.convertValue(note, Map.class));
		UpdateResponse updateResponse = null;
		try {
			updateResponse = config.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return updateResponse.getResult().name();
	}

	@Override
	public String deleteNote(Note note) {

		objectmapper.convertValue(note, Map.class);
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getNoteId()));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = config.client().delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}

	@Override
	public List<Note> searchByTitle(String title) {
//		System.out.println(title);
		SearchRequest searchRequest = new SearchRequest(INDEX);
		SearchSourceBuilder searchSource = new SearchSourceBuilder();
//		System.out.println(searchRequest);

		searchSource.query(QueryBuilders.matchQuery("title", title));
		searchRequest.source(searchSource);
		SearchResponse searchResponse = null;
		try {
			searchResponse = config.client().search(searchRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(getResult(searchResponse).toString());
		return getResult(searchResponse);
	}

	private List<Note> getResult(SearchResponse searchResponse) {
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		if (searchHits.length > 0) {
			Arrays.stream(searchHits)
					.forEach(hit -> notes.add(objectmapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
		return notes;
	}

}

package com.bridgelabz.fundoonotes.repository.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
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

@Repository
@SuppressWarnings("unchecked")
public class ElasticSearchRepositoryImpl implements IElasticSearchRepository {

	@Autowired
	private ElasticSearchConfig config;
	@Autowired
	private ObjectMapper objectmapper;

	private String INDEX = "springboot";

	private String TYPE = "note_details";

	@Override
	public String CreateNote(Note note) {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).source(notemapper);
		IndexResponse indexResponse = null;
		try {
			indexResponse = config.client().index(indexrequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(indexrequest);
		System.out.println(indexResponse);
		System.out.println(indexResponse.getResult().name());
		return indexResponse.getResult().name();

	}

	@Override
	public String UpdateNote(Note note) {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		System.out.println(note.getNoteId());
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteId())).doc(notemapper);
		UpdateResponse updateResponse = null;
		try {
			updateResponse = config.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return updateResponse.getResult().name();
	}

	@Override
	public String DeleteNote(Note note) {

		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getNoteId()));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = config.client().delete(deleterequest, RequestOptions.DEFAULT);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}

	@Override
	public List<Note> searchbytitle(String title) {
		System.out.println(title);
		SearchRequest searchrequest = new SearchRequest("springboot");
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		System.out.println(searchrequest);

		searchsource.query(QueryBuilders.matchQuery("title", title));
		searchrequest.source(searchsource);
		SearchResponse searchresponse = null;
		try {
			searchresponse = config.client().search(searchrequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getResult(searchresponse).toString());
		return getResult(searchresponse);
	}

	private List<Note> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits = searchresponse.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectmapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
		return notes;
	}

}

package search;

import com.mongodb.*;
import org.apache.lucene.search.Query;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.mapper.MapperParsingException;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.json.simple.JSONArray;
import com.mongodb.util.JSON;

import java.io.IOException;
import java.lang.Runtime;

import org.elasticsearch.node.NodeBuilder.*;
import org.elasticsearch.node.Node;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory.*;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.UnknownHostException;

public class ElasticHandler
{
    private static Client client_;
    static {
        Node node = NodeBuilder.nodeBuilder()/*.clusterName("roogle").client(true)*/.node();
        client_ = node.client();
    }

    // возвращает JSONObject = { "hits": [массив информаций о функции в формате format.json] }
    public static JSONObject getFunctions(JSONObject query)
    {
        String name = (String)query.get("name");

        SearchResponse resp = client_.prepareSearch("roogle")
                //.setTypes("string")
                //.setSearchType(SearchType.DFS_QUERY_AND_FETCH)
                .setQuery(QueryBuilders.matchQuery("name", name).fuzziness(1))
                .execute().actionGet();
        //.setPostFilter(FilterBuilders.existsFilter("name")).

        SearchHits hts = resp.getHits();
        System.out.println(String.format("Total hits: %d", hts.getTotalHits()));

        JSONObject result = new JSONObject();
        JSONArray hits = new JSONArray();
        JSONParser p = new JSONParser();

        for(SearchHit h: hts)
        {
            try
            {
                JSONObject hit = (JSONObject) p.parse(h.getSourceAsString());
                hits.add(hit);
            }
            catch (ParseException exc)
            {
                //  write log
            }

        }

        result.put("hits", hits);

        return result;
    }

    // создаёт индекс на основе файла
    public static void addToIndex(String path)
    {
        CustomParser p = new CustomParser(path); // open libcore.json
        JSONArray items = p.getFuncs();

        for(int i = 0; i < items.size(); i++)
        {
            String item = items.get(i).toString();

            client_.prepareIndex("roogle", "functions", Integer.toString(i))
                    .setSource(item).execute().actionGet();
        }
    }
}

package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CustomParser
{
    private JSONArray funcs_;

    public JSONArray getFuncs()
    {
        return funcs_;
    }

    public CustomParser(String filename)
    {
        try
        {
            FileReader rdr = new FileReader(filename);

            JSONParser parser = new JSONParser();
            funcs_ = (JSONArray) parser.parse(rdr);

            for(int i = 0; i < funcs_.size(); ++i)
            {
                JSONObject item = (JSONObject)funcs_.get(i);
                String name = (String)item.get("name");
                item = (JSONObject)item.get("inner");
                String kind = (String)item.get("kind");
                item = (JSONObject)item.get("value");
                JSONObject decl = (JSONObject)item.get("decl");
                JSONObject generics = (JSONObject)item.get("generics");

                JSONArray inputs = (JSONArray)decl.get("inputs");
                JSONObject output = (JSONObject)decl.get("output");

                for(int j = 0; j < inputs.size(); j++)
                {
                    JSONObject input = (JSONObject)inputs.get(j);
                    JSONObject type = (JSONObject)input.get("type_");
                    input.remove("type_");
                    input.put("type_", type.toString());
                }

                decl.remove("output");
                decl.put("output", output.toString());

                JSONArray type_params = (JSONArray)generics.get("type_params");
                for(int q = 0; q < type_params.size(); q++)
                {
                    JSONObject param = (JSONObject)type_params.get(q);
                    JSONArray bounds = (JSONArray)param.get("bounds");
                    param.remove("bounds");
                    param.put("bounds", bounds.toString());
                }
            }
        }
        catch (FileNotFoundException exc)
        {
            System.out.println(exc.getMessage());
        }
        catch (ParseException exc)
        {
            System.out.println(exc.getMessage());
        }
        catch(IOException exc)
        {
            System.out.println(exc.getMessage());
        }
    }
}

package ru.idr.arbitragestatistics.util;

import org.json.JSONArray;

public interface IJsonSerializable {
    
    //#region D3 Graph Serialization

    public JSONArray verticesToJsonArray();

    public JSONArray edgesToJsonArray();
    
    //#endregion

}

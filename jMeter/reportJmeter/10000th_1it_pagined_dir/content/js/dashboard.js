/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 6;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 44.82876352558042, "KoPercent": 55.17123647441958};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.2640771089400147, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.7953, 500, 1500, "character_request-0"], "isController": false}, {"data": [0.0, 500, 1500, "character_request-1"], "isController": false}, {"data": [1.0, 500, 1500, "character_request-2"], "isController": false}, {"data": [0.0, 500, 1500, "character_request"], "isController": false}, {"data": [2.5E-4, 500, 1500, "login_request"], "isController": false}, {"data": [0.024861878453038673, 500, 1500, "login_request-0"], "isController": false}, {"data": [0.0015067805123053742, 500, 1500, "login_request-1"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 38076, 21007, 55.17123647441958, 5459.553288160507, 0, 38683, 30448.0, 30815.0, 37098.990000000005, 708.0877020065832, 1358.7453820632566, 41.90559664585386], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["character_request-0", 10000, 0, 0.0, 1672.8331999999994, 0, 29931, 7441.0, 7889.0, 14914.369999999986, 218.00741225201656, 73.95714110529758, 5.926906202310879], "isController": false}, {"data": ["character_request-1", 2047, 737, 36.00390815828041, 4532.161700048849, 0, 8429, 7470.0, 7517.0, 8120.639999999999, 142.74755927475593, 372.6482141300558, 16.058119769874477], "isController": false}, {"data": ["character_request-2", 2047, 0, 0.0, 0.014167073766487532, 0, 1, 0.0, 0.0, 1.0, 240.42753112520555, 69.22846286263801, 0.0], "isController": false}, {"data": ["character_request", 10000, 9433, 94.33, 3220.498500000001, 0, 37616, 13972.0, 14456.0, 29680.97, 191.61493063539513, 648.3905880243063, 11.584600836159654], "isController": false}, {"data": ["login_request", 10000, 9423, 94.23, 10435.242799999962, 0, 38683, 30729.9, 31015.949999999997, 37675.99, 210.27840861300362, 484.3406860135945, 12.056016853814452], "isController": false}, {"data": ["login_request-0", 1991, 0, 0.0, 16950.023606228064, 105, 31060, 30144.6, 30586.399999999998, 30924.96, 49.08051077256816, 28.47053066299364, 11.64703527122467], "isController": false}, {"data": ["login_request-1", 1991, 1414, 71.01958814665997, 5809.816675037664, 0, 30983, 28543.8, 30497.8, 30854.72, 42.021063295413775, 120.549706105823, 2.128750019786412], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Percentile 1
            case 8:
            // Percentile 2
            case 9:
            // Percentile 3
            case 10:
            // Throughput
            case 11:
            // Kbytes/s
            case 12:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 19697, 93.7639834340934, 51.73074902825927], "isController": false}, {"data": ["Response was null", 1310, 6.236016565906603, 3.440487446160311], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 38076, 21007, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 19697, "Response was null", 1310, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["character_request-1", 2047, 737, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 737, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["character_request", 10000, 9433, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 8123, "Response was null", 1310, null, null, null, null, null, null], "isController": false}, {"data": ["login_request", 10000, 9423, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 9423, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["login_request-1", 1991, 1414, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: localhost:8080 failed to respond", 1414, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});

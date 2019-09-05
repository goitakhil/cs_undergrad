function setBarChartDefaults(svg, metric, y_max) {

    svg.style("background","#EEE");
    let svgnode = svg.node().getBoundingClientRect();
    let width = svgnode.width-margin.left-margin.right;
    let height = svgnode.height-margin.top-margin.bottom;
    svg.attr("width", width);
    svg.attr("height", height);

    g = svg.append("g")
        .attr("transform", `translate(${margin.left},0)`);

    y = d3.scaleLinear()
        .domain([0,y_max])
        .range([height-margin.bottom,0]);

    x = d3.scaleTime()
        .domain([
            new Date(+begin.substr(0,4),(+begin.substr(5,2))-1,+begin.substr(8,2)),
            new Date(+end.substr(0,4),(+end.substr(5,2))-1,+end.substr(8,2))
        ])
        .range([margin.left,width-margin.right]);
    yAxis = g => g
        .attr("transform", `translate(${margin.left},${margin.bottom})`)
        .call(d3.axisLeft(y))
        .call(g => g.select(".domain").remove())
        .call(g => g.select(".tick:last-of-type text").clone()
            .attr("x", 4)
            .attr("text-anchor", "start")
            .attr("font-weight", "bold")
            .text(metric.charAt(0).toUpperCase() + metric.slice(1)))

    xAxis = g => g
        .attr("transform", `translate(0,${height})`)
        .call(d3.axisBottom(x).tickFormat(d3.timeFormat("%Y-%m-%d")))
        .selectAll("text")
        .style("text-anchor", "end")
        .attr("dx", "-.8em")
        .attr("dy", ".15em")
        .attr("transform", "rotate(-35)")

    g.append("g").call(function(g){
        g.call(yAxis);
    });
    g.append("g").call(function(g){
        g.call(xAxis);
        g.attr("transform", `translate(${margin.left},${svg.attr("height")})`)
        g.call(g => g.append("text")
            .attr("x", width/2)
            .attr("y", margin.bottom+15)
            .attr("fill", "#000")
            .attr("font-weight", "bold")
            .attr("text-anchor", "middle")
            .text("Time Period"))
    });
}

function addToLegend(metric_name,nice_name,legend_id) {
   
    if ($(`.${metric_name}.Legend-item`).length == 0)
        $(`#${legend_id}`).append(`
        <div class="${metric_name} legend-item" onclick="toggleOpaque('${metric_name}')" >
            <div class="legend-color" style="background: ${getColor(metric_name)};"></div>
            <p class="legend-name">
            ${nice_name}
            </p>
        </div>
        `);
}

function toggleOpaque(className) {
    $(`circle.${className}`).toggleClass("hidden")
    $(`path.${className}`).toggleClass("hidden")
    $(`div.${className}.legend-item`).toggleClass("disabled");
}


function getColor(metric_name){
    if (metric_name == "max_core_count")
	return "#000";
    return colorScale(metric_name);
}

function removeData(metric_name) {

    d3.selectAll(`.${metric_name}`).remove();
    selections.delete(metric_name);
}


function appendWaitTimeData(svg,url,metric){

    d3.json(`${url}?${date_range}`).then(data => {
    	setBarChartDefaults(svg,"Wait Times (Hours)",data.max+5);
        let y = d3.scaleLinear()
            .domain([0, data.max+5])
            .range([svg.attr("height")-margin.bottom,0]);

        let x = d3.scaleTime()
            .domain([
                Date.parse(begin),
                Date.parse(end)
            ])
            .range([margin.left,svg.attr("width")-margin.right]);
        let g = svg.select('g');
        let tooltip = d3.select(".tooltip");

        Object.entries(data.data).forEach((wait_times) => {
            addToLegend(wait_times[0],wait_times[0],"wait-time-legend");
            g.append("path")
                .datum(wait_times[1])
                .attr("class", "line")
                .style("fill","none")
                .style("stroke", colorScale(wait_times[0]))
                .attr("class", wait_times[0])
                .attr("d", d3.line()
                    .x(d => x(Date.parse(d.date))+margin.left)
                    .y(d => y(d[metric])+margin.bottom));
            const point = g
                .selectAll("dots")
                .data(wait_times[1]).enter()
                .append("circle")
                .attr("fill", colorScale(wait_times[0]))
                .attr("cx", d => x(Date.parse(d.date))+margin.left)
                .attr("cy", d => y(+d[metric])+margin.bottom)
                .attr("class", wait_times[0])
                .attr("r", 3)
                .on("mouseover", d => {
                    tooltip.transition()
                        .duration(200)
                        .style("opacity", .9);
                    tooltip.html((wait_times[0]) +
                        "<br/>"  + (d[metric]).toFixed(2) + " Hours"+
                        "<br/>"  + (d.date))
                        .style("left", (d3.event.pageX+15) + "px")
                        .style("top", (d3.event.pageY-40) + "px");
                })
                .on("mouseout", d => {
                    tooltip.transition()
                        .duration(500)
                        .style("opacity", 0)
                });


        });
    });
}

function appendUsageData(svg,url,metric_name,nice_name,options){

    d3.json(`${url}?${date_range}&${options}`).then(data => {
        addToLegend(metric_name,nice_name,"usage-legend");
        selections.set(`${metric_name}`,
            {"url": url, 
                "options": options, 
                "name": nice_name, 
                "metric_name": metric_name});
        y = d3.scaleLinear()
            .domain([0, 1100])
            .range([svg.attr("height")-margin.bottom,0]);

        x = d3.scaleTime()
            .domain([
                Date.parse(begin),
                Date.parse(end)
            ])
            .range([margin.left,svg.attr("width")-margin.right]);

        let g = svg.select('g');
        let tooltip = d3.select(".tooltip");
        
        g.append("path")
            .datum(data)
            .attr("class", "line")
            .attr("class", metric_name)
            .style("fill","none")
            .style("stroke", getColor(metric_name))
            .attr("d", d3.line()
                .x(d => x(Date.parse(d.date))+margin.left)
                .y(d => y(d.cores)+margin.bottom));
        
        const point = g
            .selectAll("dots")
            .data(data).enter()
            .append("circle")
            .attr("class", metric_name)
            .attr("fill", getColor(metric_name))
            .attr("cx", d => x(Date.parse(d.date))+margin.left)
            .attr("cy", d => y(+d.cores)+margin.bottom)
            .attr("r", 3)
            .on("mouseover", d => {
                tooltip.transition()
                    .duration(200)
                    .style("opacity", .9);
                tooltip.html((nice_name) +
                    "<br/>"  + (d.cores).toFixed(2) + " Cores"+
                    "<br/>"  + (d.date))
                    .style("left", (d3.event.pageX+15) + "px")
                    .style("top", (d3.event.pageY-40) + "px");
            })
            .on("mouseout", d => {
                tooltip.transition()
                    .duration(500)
                    .style("opacity", 0);
            });


    });
}

function buildWaitTimeChart(id,metric) {
    //will set the background, axis, etc. 
    svg = d3.select("#"+id+"> svg");
    url = $("#"+id+ "> #url").html();
    //calls the given url and adds the returned data to the chart
    //It will also set the legend data in order to keep a consistant legend.
    appendWaitTimeData(svg,`${url}`,metric);
}

function buildUsageChart(id,metric) {
    svg = d3.select("#"+id+"> svg");
    url = $("#"+id+ "> #url").html();

    setBarChartDefaults(svg,metric,1100);
    //calls the given url and adds the returned data to the chart
    //It will also set the legend data in order to keep a consistant legend.
    appendUsageData(svg,"/api/v1/maxcores","max_core_count","Max Cores");
    appendUsageData(svg,url,"total","Total");
}

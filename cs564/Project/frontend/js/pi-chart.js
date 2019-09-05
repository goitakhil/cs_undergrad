function buildPiChart(id,cd,get_name) {
    let pie = d3.pie()
        .value(d => d.value)
    let svg = d3.select(`#${id} > svg`);
    let svgnode = svg.node().getBoundingClientRect();
    let width = svgnode.width
    let height = svgnode.height;
    let radius = d3.min([svgnode.height,svgnode.width])/2 - margin.left;
    let url = $("#"+id+ "> #url").html();
    let arc = d3.arc()
        .innerRadius(0)
        .outerRadius(radius);
    svg.attr("width", width)
        .attr("height", height);

    let g = svg.append("g").attr("transform", `translate(${width/2},${height/2})`);
    let arcLabel = d3.arc().innerRadius(radius).outerRadius(radius*0.8);

    d3.json(`${url}?${date_range}`).then(data => {
        total = data.reduce((total,d) => total + d.value,0);
        data.push({"label":"Free","value":1-total});
        let arcs = pie(data);
         div=d3.select(".tooltip");

        g.selectAll("path")
            .data(arcs)
            .enter().append("path")
            .attr("fill", d => d.data.label ? "#FFF" : colorScale(`${cd}_${d.data.id}_usage`))
            .attr("label",d => d.data.label ? d.data.label : get_name(d.data.id))
            .attr("stroke", "black")
            .attr("d", arc)
        .on("mouseover", d => {
            div.transition()
                .duration(200)
                .style("opacity", .9);
            div.html((d.data.label?d.data.label:get_name(d.data.id)) +
                "<br/>"  + (d.data.value*100).toFixed(2) + "%")
                .style("left", (d3.event.pageX) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
            })
        .on("mouseout", d => {
            div.transition()
                .duration(500)
                .style("opacity", 0);
        });
    })
}

function buildDiskPiCharts() {
    let url = "/api/v1/disk";

    buildDiskPi = (svg,name) => {
    }

    d3.json(`${url}?${date_range}`).then(data => {

            let pie = d3.pie()
                .value(d => d.value)
		        .sort((a,b) => {
                    return b.label.localeCompare(a.label);
                });
        Object.keys(data).forEach(k => {
            let svg = d3.select(`#${k}-usage > svg`)
            let svgnode = svg.node().getBoundingClientRect();
            let width = svgnode.width
            let height = svgnode.height;
            let radius = d3.min([svgnode.height,svgnode.width])/2 - margin.left;
            let arc = d3.arc()
                .innerRadius(0)
                .outerRadius(radius);
            svg.attr("width", width)
                .attr("height", height);

            let g = svg.append("g").attr("transform", `translate(${width/2},${height/2})`);
            let arcLabel = d3.arc().innerRadius(radius).outerRadius(radius*0.8);
            let innerData = [{"label":"Free","value":data[k].total-data[k].used},{"label":"Used","value":data[k].used}]
            let arcs = pie(innerData);

            div=d3.select(".tooltip");

            g.selectAll("path")
                .data(arcs)
                .enter().append("path")
                .attr("fill", d => d.data.label == "Used" ? "#0033A0" : "#D64309")
                .attr("label",d => d.data.label)
                .attr("stroke", "black")
                .attr("d", arc)
                .on("mouseover", d => {
                    div.transition()
                        .duration(200)
                        .style("opacity", .9);
                    div.html((d.data.label) +
                        "<br/>"  + (d.data.value).toFixed(2) + "TB")
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                })
                .on("mouseout", d => {
                    div.transition()
                        .duration(500)
                        .style("opacity", 0);
                });
        });
    })
}

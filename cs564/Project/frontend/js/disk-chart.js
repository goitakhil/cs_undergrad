function buildDiskBarCharts() {
    let url = "/api/v1/disk";

    d3.json(`${url}?${date_range}`).then(data => {

        Object.keys(data).forEach(k => {
            let svg = d3.select(`#${k}-usage > svg`)
            let width = 273;
            let height = 100;
            svg.attr("width", width)
               .attr("height", height);

            let g = svg.append("g");
            let innerData = [{"label":"Free","value":data[k].total-data[k].used},{"label":"Used","value":data[k].used}]

            let div=d3.select(".tooltip");
            
            x = d3.scaleLinear()
                .domain([0, data[k].total])
                .range([margin.right,width-margin.left])
                g.append('rect')
                    .attr("x", margin.left)
                    .attr("y", height/2-40)
                    .attr("width",  width-margin.left-margin.right)
                    .attr("height", 40)
                    .attr("fill", "#FFF")
                    .attr("stroke", "#000")
                .on("mouseover", d => {
                    div.transition()
                        .duration(200)
                        .style("opacity", .9);
                    div.html(
                        `Free:
                        <br/>
                        ${(+data[k].total-(+data[k].used)).toFixed(2)} TB`)
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                })
                .on("mouseout", d => {
                    div.transition()
                        .duration(500)
                        .style("opacity", 0);
                });

                g.append('rect')
                    .attr("x", margin.left)
                    .attr("y", height/2-40)
                    .attr("width",  x(data[k].used))
                    .attr("height", 40)
                    .attr("fill", "#0033A0")
                .on("mouseover", d => {
                    div.transition()
                        .duration(200)
                        .style("opacity", .9);
                    div.html(
                        `Used:
                        <br/>
                        ${((+data[k].used)).toFixed(2)} TB`)
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                })

                g.append('text')
                    .text(`${((+data[k].used)).toFixed(2)} TB Used`)
                    .attr("y",height-20)
                    .attr("text-anchor", "start")
                    .attr("x",0)
                
                g.append('text')
                    .text(`${(+data[k].total - (+data[k].used)).toFixed(2)} TB Free`)
                    .attr("y",height-20)
                    .attr("text-anchor", "end")
                    .attr("x",width)

                .on("mouseout", d => {
                    div.transition()
                        .duration(500)
                        .style("opacity", 0);
                });
        });
    })
}

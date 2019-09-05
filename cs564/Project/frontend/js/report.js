var margin, begin, end, date_range, colleges,departments,dept_to_col,selections;
var div = d3.select(".tooltip")

function renderCharts() {
    getInput();
    $(`#wait-time-graph`).parent().addClass("invisible");
    buildUsageChart("util-graph","cores");
    buildWaitTimeChart("wait-time-graph","waitave");
    buildPiChart("util-by-col","college",(id) => colleges[id]);
    buildPiChart("util-by-dept","dept",(id) => departments[id]);
    buildDiskBarCharts();
}

selections = new Map();

function redraw() {
    getInput();
    let beginningDate = new Date(begin)
    let endDate = new Date(end)


    d3.selectAll(".bar-chart").selectAll("*").remove();
    d3.select("#util-by-col > svg").selectAll("*").remove();
    d3.select("#util-by-dept > svg").selectAll("*").remove();
    d3.select(".legend").selectAll("*").remove();
    buildPiChart("util-by-col","college",(id) => colleges[id]);
    buildPiChart("util-by-dept","dept",(id) => departments[id]);
    buildUsageChart("util-graph","cores");
    buildWaitTimeChart("wait-time-graph","waitave");
    if (selections.size > 0) {
        svg = d3.select("#util-graph > svg")
        selections.forEach((v,k,m)=>{
            if (k!="total_usage"){
                selections.delete(k);
                appendUsageData(svg,v.url,v.metric_name,v.name,v.options);
            }
        })
    }
}

function openChart(evt, chartName) {
    var i, tabcontent, tablinks;
    tabcontent = $(".tabcontent").addClass("invisible");
    tablinks = $(".tablinks").removeClass("active");
    $(`#${chartName}`).removeClass("invisible");
    evt.currentTarget.className += " active";
}

function getInput() {
    begin = $("#beginning").val();
    end = $("#end").val();
    date_range = "start="+begin+"&end="+end;
}

function toggleCollegeFilter(checkbox) {
    if (checkbox.checked) {
        opt = `college_id=${checkbox.value}`;
        appendUsageData(d3.select("#util-graph > svg"),
            `/api/v1/college`,`college_${checkbox.value}_usage`,
            colleges[checkbox.value],opt);
    } else {
        removeData(`college_${checkbox.value}_usage`);
    }
}

function toggleDepartmentFilter(checkbox) {
    if (checkbox.checked) {
        opt = `&dept_id=${checkbox.value}`;
        appendUsageData(d3.select("#util-graph > svg"),
            `/api/v1/dept`,`dept_${checkbox.value}_usage`,
            departments[checkbox.value],opt);
    } else {
        removeData(`dept_${checkbox.value}_usage`);
    }
}

function setFilters(colleges, departments, partitions) {
    Object.keys(colleges).forEach((k) => {
        $("#college-list").append(`
            <div class="filter-selector">
                <input type="checkbox" name="${k}" value="${k}" onclick="toggleCollegeFilter(this)"></input>
                <p class="filter-label">${colleges[k]}</p>
            </div>
            `)
    })
    Object.keys(departments).forEach((k) => {
        $("#department-list").append(`
            <div class="filter-selector">
                <input type="checkbox" name="${k}" value="${k}" onclick="toggleDepartmentFilter(this)"></input>
                <p class="filter-label">${departments[k]}</p>
            </div>
            `)
    })

}

async function setDefaults() {
    margin = {top: 20, right: 30, bottom: 40, left: 30};
    
    let defEnd = new Date();
    let defBegin = new Date();

    //start yesterday
    defEnd.setDate(defEnd.getDate()-1);
    defBegin.setDate(defBegin.getDate()-1);

    //1 month prior
    defBegin.setMonth(defBegin.getMonth()-1);

    //parse year-month-day
    let endDay = ("0" + (defEnd.getDate())).slice(-2);
    let endMonth = ("0" + (defEnd.getMonth()+1)).slice(-2);
    let beginMonth = ("0" + (defBegin.getMonth()+1)).slice(-2);
    let beginDay = ("0" + (defEnd.getDate())).slice(-2);
    
    let beginning = $("#beginning").get(0); 
    beginning.value = `${defBegin.getFullYear()}-${beginMonth}-${beginDay}`;
    
    let end = $("#end").get(0);
    end.value = `${defEnd.getFullYear()}-${endMonth}-${endDay}`;

    beginning.max = end.value;
    beginning.min = "2017-01-01";
    end.max = end.value;
    end.min = beginning.min;
    //We need the config values before anything happens
    let config = await d3.json("/api/v1/config")
    colleges = config.colleges;
    Object.keys(colleges).forEach(k => colorScale(`college_${k}_usage`));
    departments = config.departments;
    Object.keys(departments).forEach(k => colorScale(`dept_${k}_usage`));
    dept_to_col = config.dept_to_col;
    setFilters(colleges, departments,null);
    renderCharts();

}

var palette = [
    "#03c70e",
    "#b81114",
    "#6f0ffb",
    "#41b6ea",
    "#fd7cdf",
    "#596021",
    "#0355c7",
    "#ea9a17",
    "#18c19a",
    "#76506c",
    "#ed9482",
    "#a4b41b",
    "#b4075c",
    "#146665",
    "#9e03b5",
    "#a8ad9b",
    "#b29ff2",
    "#8d4a15",
    "#186095",
    "#c7a75e",
    "#834096",
    "#69be5b",
    "#e193be",
    "#3644e7",
    "#196b0a",
    "#9a3e44",
    "#5cb9bc",
    "#a7a9ca",
    "#735642",
    "#ff8c53",
    "#87b780",
    "#ac028b",
    "#166941",
    "#5154a2",
    "#973873",
    "#6f41bb",
    "#cda0a3",
    "#c7a92c",
    "#6dbf25",
    "#a3b356",
    "#b5143a",
    "#4c5d71",
    "#4f6147",
    "#6daeff",
    "#fc889d",
    "#d88ef9",
    "#cba481",
    "#27c54b",
    "#7c24da",
    "#a23826",
    "#765706",
    "#e49b59",
    "#24c378",
    "#aeae7a",
    "#fe82be",
    "#922da2",
    "#d992de",
    "#854c4f",
    "#8fb1bc",
    "#5c5683",
    "#fd76fa",
    "#c69dd1",
    "#7e487e",
    "#8e4361",
    "#323afa",
    "#384bd4",
    "#89ba45",
    "#036383",
    "#a43056",
    "#ff8f08",
    "#615b5a",
    "#346727",
    "#7d532a",
    "#9ca8e4",
    "#86b5a1",
    "#8f4831",
    "#9f3c03",
    "#07bfb5",
    "#65be78",
    "#e495a3",
    "#af0f73",
    "#2cbbd6",
    "#64bb9a",
    "#564eb4",
    "#7ab2d7",
    "#89b964",
    "#7934c7",
    "#98308a",
    "#df9c75",
    "#fb8d6f",
    "#685b2f",
    "#dca041",
    "#f19543",
    "#b4a8af",
    "#035ab4",
    "#c2a1bd",
    "#486435",
    "#b9ad47",
    "#49650d",
    "#98b38e",
    "#49605f",
    "#6f4d90",
    "#f187d2",
    "#7aafeb",
    "#d99d8f",
    "#0c6853",
    "#625e02",
    "#28c462",
    "#a82f3f",
    "#88bb17",
    "#5cc143",
    "#3b5a9b",
    "#b7af09",
    "#15b6ff",
    "#b31e28",
    "#745454",
    "#bfa795",
    "#94a7f8",
    "#fc8a8a",
    "#d9a223",
    "#ef82f3",
    "#9210c8",
    "#8c3e84",
    "#c897f2",
    "#65576c",
    "#ac2d07",
    "#455d83",
    "#8731b5",
    "#b3ae65",
    "#625c48",
    "#9db46b",
    "#a7b33d",
    "#4cc323",
    "#7d3ea8",
    "#684ca2",
    "#a50c9d",
    "#caa748",
    "#b7024b",
    "#306371",
    "#076b26",
    "#7e13e7",
    "#376547",
    "#6b5b1a",
    "#a92467",
    "#e989e5",
    "#0fbdc9",
    "#3fc186",
    "#c2a873",
    "#d7a166",
    "#72b5c9",
    "#b7ab88",
    "#d49bb0",
    "#fd8f32",
    "#ed9667",
    "#5b40d4",
    "#844e3d",
    "#f789b1",
    "#5c36e7",
    "#57be8d",
    "#e88ecb",
    "#4f4cc1",
    "#7db894",
    "#954425",
    "#ada5d7",
    "#94b832",
    "#52c071",
    "#aa2e2d",
    "#b09dff",
    "#a42679",
    "#ae2150",
    "#5a558f",
    "#ff7fcb",
    "#7f5213",
    "#ee9296",
    "#87b4af",
    "#256189",
    "#8d4655",
    "#894472",
    "#704f84",
    "#d39ac4",
    "#9c3d32",
    "#17c639",
    "#595f3b",
    "#4b53ae",
    "#8b389c",
    "#2656ba",
    "#cfa710",
    "#3d6359",
    "#64baa8",
    "#575e54",
    "#d4a358",
    "#b3a9a2",
    "#f39527",
    "#8c4843",
    "#b5af35",
    "#4ab8dd",
    "#90acde",
    "#6b584e",
    "#9c3661",
    "#a2354a",
    "#c69bde",
    "#a3acb6",
    "#7c5149",
    "#9bb0a8",
    "#755260",
    "#745722",
    "#575a77",
    "#415b8f",
    "#58b3f1",
    "#6a5a3c",
    "#8eafc9",
    "#ee8faa",
    "#88abf2",
    "#6a5378",
    "#db9b9c",
    "#834a66",
    "#14c0a7",
    "#662bed",
    "#9a1fa9",
    "#8f03d4",
    "#8b25c1",
    "#b0261a",
    "#35663b",
    "#e89b39",
    "#98347e",
    "#76bc6a",
    "#6b46ae",
    "#96440d",
    "#7bbd3b",
    "#97b74d",
    "#116a34",
    "#f4935a",
    "#b4a1e5",
    "#2651cd",
    "#d995d1",
    "#72bb86",
    "#7e468a",
    "#a52085",
    "#d2a537",
    "#bcac57",
    "#6438da",
    "#d990ec",
    "#7ebb54",
    "#5946c7",
    "#234ae0",
    "#9d2496",
    "#48c253",
    "#306816",
    "#1b42f4",
    "#874d24",
    "#fe79ec",
    "#99b479",
    "#215ba8"
];

var colorScale = d3.scaleOrdinal(palette).domain(["total"]);

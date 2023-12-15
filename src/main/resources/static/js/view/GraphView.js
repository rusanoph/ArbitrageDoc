
export class GraphView {
    d3 = d3;

    #viewId;
    #width;
    #height;

    #vertexRadius = 7;
    #vertexTextFonSize = 14;
    #vertexTextMargin = {x: 15, y: 4};

    svg;

    #nodes;
    #links;

    #d3Nodes;
    #d3Links;
    #d3Text;

    #simulation;

    #zoom;
    #resetTransform = {x: 0, y: 0, k: 1}


    //#region Constructor & Creation 
    constructor(viewId, width, height, nodes, links) {
        console.log(this.d3);

        this.#width = width;
        this.#height = height;
        this.#nodes = nodes;
        this.#links = links;

        this.#viewId = viewId;

        this.initGraph();

        console.log(`Graph '${viewId}' initialized`);
    }

    initGraph() {
        this.svg = d3.select(`#${this.#viewId}`).append("svg")
            .attr("width", this.#width)
            .attr("height", this.#height)
            .call(d3.zoom()
                .on("zoom", this.handleZoom))
            .append("g");

        this.#simulation = d3.forceSimulation(this.#nodes)
            .force("link", d3.forceLink(this.#links).id(d => d.id))
            // .force("charge", this.attractionForce)
            .force("charge", d3.forceManyBody())
            .force("center", d3.forceCenter(this.#width / 2, this.#height / 2));

        this.#d3Links = this.svg.selectAll("line")
            .data(this.#links)
            .enter().append("line")
            .attr("stroke", "#333")
            .attr("stroke-width", "2");

        //#region Vertex initialization
        this.#d3Nodes = this.svg.selectAll("circle")
            .data(this.#nodes)
            .enter().append("circle")
            .style("stroke", d => d.hasAction ? "#2299ab" : "transparent")
            .style("stroke-width", "4")
            .attr("r", this.#vertexRadius)
            .attr("fill", "#555");

        this.#d3Nodes.append("title")
            .text(d => d.id);
        
        this.#d3Nodes.call(d3.drag()
            .on("start", this.dragStarted)
            .on("drag", this.dragged)
            .on("end", this.dragEnded));
        //#endregion

        this.#d3Text = this.svg.selectAll("text")
            .data(this.#nodes)
            .enter().append("text")
            .text(d => d.value)
            .attr("font-size", this.#vertexTextFonSize)
            .attr("x", this.#vertexTextMargin.x)
            .attr("y",this.#vertexTextMargin.y);
    }

    startSimulation() {
        this.#simulation.on("tick", () => {
            this.#d3Links   
                .attr("x1", d => d.source.x)
                .attr("y1", d => d.source.y)
                .attr("x2", d => d.target.x)
                .attr("y2", d => d.target.y);
        
            this.#d3Nodes
                .attr("cx", d => d.x)
                .attr("cy", d => d.y);
        
            this.#d3Text
                .attr("x", d => d.x + this.#vertexTextMargin.x)
                .attr("y", d => d.y + this.#vertexTextMargin.y);
        });

        this.timerBeforeStop(100);
    }
    //#endregion

    //#region Getter/Setter
    getNodes() { return this.#nodes; }
    getLinks() { return this.#links; }
    
    getSimulation() { return this.#simulation; }

    getZoom() { return this.#zoom; }

    getD3Nodes() { return this.#d3Nodes; }
    getD3Links() { return this.#d3Links; }
    getD3Text() { return this.#d3Text; }

    //#endregion

    //#region Graph Control
    handleZoom() {
        this.#zoom = d3.event.transform;
        this.svg.attr("transform", this.#zoom);
    }

    handleZoomReset() {
        this.svg.attr("transform", `translate(${this.#resetTransform.x}, ${this.#resetTransform.y}) scale(${this.#resetTransform.k})`);
        this.#zoom.x = this.#resetTransform.x;
        this.#zoom.y = this.#resetTransform.y;
        this.#zoom.k = this.#resetTransform.k;
    }

    handlePositionReset() {
        this.#simulation.force("link", d3.forceLink(this.#links).id(d => d.id))
            // .force("charge", startForce)
            .force("charge", d3.forceManyBody())
            .force("center", d3.forceCenter(this.#width / 2, this.#height / 2));

        this.timerBeforeStop(1000);
    }
    //#endregion

    //#region Drag Graph Nodes
    dragStarted(d) {
        if (!d3.event.active) this.getSimulation().alphaTarget(1).restart();
        d.fx = d.x;
        d.fy = d.y;
    }

    dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    }

    dragEnded(d) {
        if (!d3.event.active) this.getSimulation().alphaTarget(0);
        d.fx = null;
        d.fy = null;
    }
    //#endregion

    //#region Simulation
    timerBeforeStop(ms) {
        setTimeout(() => {
            this.#simulation.force("charge", this.noForce);
            this.#simulation.alpha(1).restart();
        }, ms);
    }

    //#region Default Forces
    // Force method must have one 'alpha' parameter: force(alpha) -> { ... }
    noForce(alpha) {
        console.log(this.nodes);

        for (let node of this.#nodes) {
            node.vx = 0;
            node.vy = 0;
        }
    }

    retractionForce(alpha) {
        const k = alpha * 0.707;

        for (let node of this.#nodes) {
            node.vx += node.x * k;
            node.vy += node.y * k;
        }
    }

    attractionForce(alpha) {
        const k = alpha * (-0.01);

        for (let node of this.#nodes) {
            node.vx += node.x * k;
            node.vy += node.y * k;
        }
    }
    //#endregion

    //#endregion
}

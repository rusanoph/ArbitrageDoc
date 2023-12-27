import { Util } from "../util/Util.js";
import { Decorator } from "../util/Decorator.js";

export class GraphView {
    viewId;
    width;
    height;

    vertexRadius = 10;
    vertexTextFonSize = 14;
    vertexTextMargin = {x: 15, y: 4};

    svg;

    nodes;
    links;

    d3Nodes;
    d3Links;
    d3Text;

    simulation;
    forceAction;

    zoom;
    resetTransform = {x: 0, y: 0, k: 1}


    //#region Constructor & Creation 
    constructor(viewId, width, height, nodes, links) {
        //#region Arguments Assigning
        this.width = width;
        this.height = height;
        this.nodes = nodes;
        this.links = links;

        this.forceAction = d3.forceManyBody();

        this.viewId = viewId;
        //#endregion

        //#region Methods Decorating
        for (let method of Object.getOwnPropertyNames(GraphView.prototype)) {
            // Dont decorate constructor
            if (method === "constructor") {
                continue;
            }

            this[method] = Decorator.withThisClosure(this[method].bind(this));
        }
        
        //#endregion

        this.initGraph();
    }

    initGraph() {
        this.svg = d3.select(`#${this.viewId}`).append("svg")
            .attr("width", this.width)
            .attr("height", this.height)
            .call(d3.zoom()
                .on("zoom", this.handleZoom))
            .append("g");

        this.svg.append("svg:defs").selectAll("marker")
            .data(["end"])              
        .enter().append("svg:marker")    
            .attr("id", String)
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 18)
            .attr("refY", 0)
            .attr("markerWidth", 5)
            .attr("markerHeight", 5)
            .attr("orient", "auto")
            .attr("fill", "#333")
        .append("svg:path")
            .attr("d", "M0,-5L10,0L0,5");

        this.simulation = d3.forceSimulation(this.nodes)
            .force("link", d3.forceLink(this.links)
                            .id(d => d.id)
                            .distance(d => 175)
            )
            .force("charge", this.forceAction)
            .force("center", d3.forceCenter(this.width / 2, this.height / 2));

        this.d3Links = this.svg.selectAll("line")
            .data(this.links)
            .enter().append("line")
            .attr("stroke", "#333")
            .attr("stroke-width", "2")
            .attr("marker-end", "url(#end)");;
        

        //#region Vertex initialization
        this.d3Nodes = this.svg.selectAll("circle")
            .data(this.nodes)
            .enter().append("circle")
            .style("stroke", this.nodeClassifier)
            .style("stroke-width", "4")
            .attr("r", this.vertexRadius)
            .attr("fill", "#555");

        this.d3Nodes.append("title")
            .text(d => `${d.id}`);
        
        //#region Node events
        this.d3Nodes.on("contextmenu", this.vertexOpenModalRightClickClosure("graph-vertex-modal"));

        this.d3Nodes.on("mouseover", d => document.body.style.cursor = "pointer")
                    .on("mouseout", d => document.body.style.cursor = "default");

        this.d3Nodes.call(d3.drag()
            .on("start", this.dragStarted)
            .on("drag", this.dragged)
            .on("end", this.dragEnded));
        //#endregion

        //#endregion

        this.d3Text = this.svg.selectAll("text")
            .data(this.nodes)
            .enter().append("text")
            .text(d => this.textMinifier(d))
            .attr("font-size", this.vertexTextFonSize)
            .attr("x", this.vertexTextMargin.x)
            .attr("y",this.vertexTextMargin.y);
    }

    startSimulation() {
        this.simulation.on("tick", () => {
            this.d3Links   
                .attr("x1", d => d.source.x)
                .attr("y1", d => d.source.y)
                .attr("x2", d => d.target.x)
                .attr("y2", d => d.target.y);
        
            this.d3Nodes
                .attr("cx", d => d.x)
                .attr("cy", d => d.y);
        
            this.d3Text
                .attr("x", d => d.x + this.vertexTextMargin.x)
                .attr("y", d => d.y + this.vertexTextMargin.y);
        });

        // this.timerBeforeStop(100);
    }
    //#endregion

    //#region Getter/Setter
    getNodes() { return this.nodes; }
    getLinks() { return this.links; }
    
    getSimulation() { return this.simulation; }

    getZoom() { return this.zoom; }

    getD3Nodes() { return this.d3Nodes; }
    getD3Links() { return this.d3Links; }
    getD3Text() { return this.d3Text; }

    setAttractionForceAction() { this.setForceAction(this.attractionForce); }
    setRetractionForceAction() { this.setForceAction(this.retractionForce); }
    setNoForceAction() { this.setForceAction(this.noForce); }
    setBalanceForceAction() { this.setForceAction(d3.forceManyBody()); }

    setForceAction(force) { 
        this.forceAction = force;

        this.simulation.force("link", d3.forceLink(this.links).id(d => d.id))
        .force("charge", this.forceAction)
        .force("center", d3.forceCenter(this.width / 2, this.height / 2));

        this.simulation.alpha(1).restart();
    }

    //#endregion

    //#region Graph Control
    textMinifier(nodeData) {
        return nodeData.value.length > 15 ? nodeData.value.slice(0, 15) + "..." : nodeData.value;
    }

    linkIn(nodeData) {
        return this.links.filter(link => link.target === nodeData).length;
    }

    linkOut(nodeData) {
        return this.links.filter(link => link.source === nodeData).length;
    }

    linkLength(nodeData) {
        const nodeConnections = this.links.filter(link => link.source === nodeData || link.target === nodeData).length;

        return nodeConnections;
    }

    nodeClassifier(nodeData) {
        if (nodeData.value === "Initial") return "#FFD700";
        if (nodeData.hasAction) return "#4682B4";

        return "transparent";
    }

    vertexOpenModalRightClickClosure(modalId) {
        return (d) => {
            let modalHtml = `
            <div class="key-value-header">
                <div class="value">Атрибут узла</div>
            </div>
            <div class="list">
                <div class="key-value-row">
                    <div class="key">Id: </div>
                    <div class="value copyable" onclick="copyToClipboard(this)">${d.id}</div>
                </div>
                <div class="key-value-row">
                    <div class="key">Значение: </div>
                    <div class="value copyable" onclick="copyToClipboard(this)">${d.value}</div>
                </div>
                <div class="key-value-row">
                    <div class="key">Глубина: </div>
                    <div class="value">${d.depth}</div>
                </div>
                <div class="key-value-row">
                    <div class="key">Наличие действия на узле: </div>
                    <div class="value">${d.hasAction}</div>
                </div>
                <div class="key-value-row">
                    <div class="key">Входящих рёбер: </div>
                    <div class="value">${this.linkIn(d)}</div>
                </div>
                <div class="key-value-row">
                    <div class="key">Исходящих рёбер: </div>
                    <div class="value">${this.linkOut(d)}</div>
                </div>
            </div>
            `;

            modalOpen(modalId, modalHtml);
            
            // Hide right click context menu
            event.preventDefault();
            return false;
        }
    }

    handleZoom() {
        this.zoom = d3.event.transform;
        this.svg.attr("transform", this.zoom);
    }

    handleZoomReset() {
        this.svg.attr("transform", `translate(${this.resetTransform.x}, ${this.resetTransform.y}) scale(${this.resetTransform.k})`);
        this.zoom.x = this.resetTransform.x;
        this.zoom.y = this.resetTransform.y;
        this.zoom.k = this.resetTransform.k;
    }

    handlePositionReset() {
        this.simulation.force("link", d3.forceLink(this.links).id(d => d.id))
            // .force("charge", this.attractionForce)
            .force("charge", d3.forceManyBody())
            .force("center", d3.forceCenter(this.width / 2, this.height / 2));

        // this.timerBeforeStop(1000);
    }

    async handleSvgSave() {
        const svgUrl = this.generateSvgSaveLink();
        const svgBlob = await (await fetch(svgUrl)).blob();
        
        const fileDialogOptions = {
            startIn: "pictures",
            types: [
                {
                    description: "SVG file",
                    accept: { "image/svg+xml": [".svg"] },
                }
            ],
        }
        const fileHandle = await window.showSaveFilePicker(fileDialogOptions);
        const writable = await fileHandle.createWritable();

        await writable.write(svgBlob);
        await writable.close();
    }

    generateSvgSaveLink() {
        const serializer = new XMLSerializer();

        const svgToSave = document.getElementById(this.viewId).children[0];

        let source = serializer.serializeToString(svgToSave);

        //add name spaces.
        if(!source.match(/^<svg[^>]+xmlns="http\:\/\/www\.w3\.org\/2000\/svg"/)){
            source = source.replace(/^<svg/, '<svg xmlns="http://www.w3.org/2000/svg"');
        }

        if(!source.match(/^<svg[^>]+"http\:\/\/www\.w3\.org\/1999\/xlink"/)){
            source = source.replace(/^<svg/, '<svg xmlns:xlink="http://www.w3.org/1999/xlink"');
        }

        source = `<?xml version="1.0" standalone="no"?>\r\n${source}`;

        const downloadLinkUrl = `data:image/svg+xml;charset=utf-8,${encodeURIComponent(source)}`;

        return downloadLinkUrl;
    }
    //#endregion

    //#region Drag Graph Nodes
    dragStarted(d) {
        document.body.style.cursor = "grabbing";

        if (!d3.event.active) this.getSimulation().alphaTarget(1).restart();
        d.fx = d.x;
        d.fy = d.y;
    }

    dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    }

    dragEnded(d) {
        document.body.style.cursor = "default";

        if (!d3.event.active) this.getSimulation().alphaTarget(0);
        d.fx = null;
        d.fy = null;
    }
    //#endregion

    //#region Simulation Handlers
    timerBeforeStop(ms) {
        setTimeout(() => {
            this.simulation.force("charge", this.noForce);
            this.simulation.alpha(1).restart();
        }, ms);
    }

    //#region Default Forces
    // Force method must have one 'alpha' parameter: force(alpha) -> { ... }
    noForce(alpha) {
        for (let node of this.nodes) {
            node.vx = 0;
            node.vy = 0;
        }
    }

    explosionForce(alpha) {
        const k = alpha * 0.707;

        for (let node of this.nodes) {
            node.vx += node.x * k;
            node.vy += node.y * k;
        }
    }

    retractionForce(alpha) {
        const k = alpha * -0.04;

        for (let node of this.nodes) {
            node.vx -= node.x * 2*k;
            node.vy -= node.y * k;
        }
    }

    attractionForce(alpha) {
        const k = alpha * (0.01);

        for (let node of this.nodes) {
            node.vx += node.x * k;
            node.vy += node.y * k;
        }
    }
    //#endregion

    //#endregion
}

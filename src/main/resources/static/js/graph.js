import { GraphView } from "./view/graphview.js";

const sideRatio = 16 / 9;
const width = 1280;
const height = width / sideRatio;

// const testGraph2GraphView = new GraphView("graph-test-tree", width, height, verticesTestGraph_2Json, edgesTestGraph_2Json);
// testGraph2GraphView.startSimulation();

// const testGraph1GraphView = new GraphView("graph-test-tree", width, height, verticesTestGraph_1Json, edgesTestGraph_1Json);
// testGraph1GraphView.startSimulation();

// const testTreeGraphView = new GraphView("graph-test-tree", width, height, verticesTestTreeJson, edgesTestTreeJson);
// testTreeGraphView.startSimulation();

const cdpTreeGraphView = new GraphView("graph-test-tree", width, height, verticesCdpTreeJson, edgesCdpTreeJson);
cdpTreeGraphView.startSimulation();


let buttonBindTree = cdpTreeGraphView;
let buttonBindTreeIndex = 0;

const zoomResetButton = document.getElementsByClassName("zoom-reset");
zoomResetButton[buttonBindTreeIndex].addEventListener('click', buttonBindTree.handleZoomReset);

const saveAsSvgButton = document.getElementsByClassName("save-graph-as-svg");
saveAsSvgButton[buttonBindTreeIndex].addEventListener('click', buttonBindTree.handleSvgSave);

const forceSelect = document.getElementsByClassName("force-select");
forceSelect[buttonBindTreeIndex].addEventListener('change', () => {
    const selectedForce = forceSelect[buttonBindTreeIndex].value;

    const forceActions = {
        attractionForce: buttonBindTree.setAttractionForceAction,
        balanceForce: buttonBindTree.setBalanceForceAction,
        retractionForce: buttonBindTree.setRetractionForceAction,
        noForce: buttonBindTree.setNoForceAction,
    }

    forceActions[selectedForce]();
});
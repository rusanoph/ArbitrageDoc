import { Util } from "./util/Util.js";
import { GraphView } from "./view/GraphView.js";


const sideRatio = 16 / 9;
const width = 1200;
const height = width / sideRatio;

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
forceSelect[buttonBindTreeIndex].addEventListener(event, () => {
    const selectedForce = forceSelect[buttonBindTreeIndex].value;

    const forceActions = {
        attractionForce: buttonBindTree.setAttractionForceAction,
        balanceForce: buttonBindTree.setBalanceForceAction,
        retractionForce: buttonBindTree.setRetractionForceAction,
        noForce: buttonBindTree.setNoForceAction,
    }

    forceActions[selectedForce]();

    console.log(selectedForce);
});
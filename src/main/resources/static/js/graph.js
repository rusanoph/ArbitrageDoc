import { GraphView } from "./view/GraphView.js";



const width = 800;
const height = 600;

const testTreeGraphView = new GraphView("graph-test-tree", width, height, verticesTestTreeJson, edgesTestTreeJson);
testTreeGraphView.startSimulation();

// const cdpTreeGraphView = new GraphView("graph-test-tree", width, height, verticesCdpTreeJson, edgesCdpTreeJson);
// cdpTreeGraphView.startSimulation();
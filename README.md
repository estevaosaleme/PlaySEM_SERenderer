# Intro

The **PlaySEM Sensory Effects Renderer (SER)** allows developers to add multi-stimuli in their own multimedia applications by interacting with it without needing to know how to process sensory effect metadata and manipulate sensory effects hardware. It provides a bus of services (UPnP, CoAP, MQTT, and WebSocket) for timeline (e.g. video players) or event-based applications (e.g. games) to integrate with it.

Related papers: 
* [PlaySEM: a Platform for Rendering MulSeMedia Compatible with MPEG-V](http://dx.doi.org/10.1145/2820426.2820450) - Webmedia'15
* [An Event-Driven Approach to Integrating Multi-sensory Effects to Interactive Environments](https://doi.org/10.1109/SMC.2015.178) - IEEE SMC'15
* [Time Evaluation for the Integration of a Gestural Interactive Application with a Distributed Mulsemedia Platform](https://doi.org/10.1145/3083187.3084013) - ACM MMSys'17
* Improving Response Time Interval in Networked Event-Based Mulsemedia Systems - ACM MMSys'18 (in press)

# Pre-requisites
* Java 1.7 or greater (http://www.java.com/en/download)
* RXTX library (http://rxtx.qbang.org/wiki/index.php/Installation or http://www.agaveblue.org/howtos/Comm_How-To.shtml). Not required for emulation.

# Running:
* [Download](https://github.com/estevaosaleme/PlaySEM_SERenderer/releases) the last release
* Uncompress the file PlaySEM_SERenderer_< version >.zip
* Run the command `java -jar PlaySEM_SERenderer.jar -Dcom.sun.xml.internal.bind.v2.runtime.JAXBContextImpl.fastBoot=true` (Please, check if you need to update the name of the jar file)

# Configuration (config.properties):<br />
`### PlaySEM SE Renderer - Settings`<br />
`# This option disables communication with real devices and uses the console to output the commands.`<br />
`emulateDevices=true`<br />
`# It shows information about processing in the console.`<br />
`debugMode=true`<br />
`# It specifies a serial port for communication with devices.`<br />
`serial_port=COM3`
<br />
`# It specifies a protocol to communicate with multimedia applications (it requires the version 1.1.0 or higher). Choices: UPNP, COAP, MQTT, WEBSOCKET.`<br />
`communicationMode=UPNP`
***
Please, report any application crash (or bug) via [issue tracker](https://github.com/estevaosaleme/PlaySEM_SERenderer/issues).<br />
Visit the **PlaySEM SE Video Player** repository at https://github.com/estevaosaleme/PlaySEM_SEVideoPlayer

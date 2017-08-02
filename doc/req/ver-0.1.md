# Requirements - version 0.1

## Home screen

### *REQ-0.1-A01* Tool launcher on the home screen

The application should be implemented as a set of tools, each tool used for one task.

When the application is started, a list of available tools should be displayed.
Tapping on any given tool should start this tool.
Leaving the tool should bring you back to the tool launcher.

## Configuration

### *REQ-0.1-B01* Adding Dropbox account to the configuration

It should be possible to enter the details of your Dropbox account so that you can later
export data to this Dropbox account or import data from it.

### *REQ-0.1-B02* Adding GitHub account to the configuration

It should be possible to enter the credentials of your github account and the name of the repository,
so that you can upload your data to github.

### *REQ-0.1-B03* Registering languages

It should be possible to register languages in the application. Language record should consist of:

- Language ID, 3 uppercase characters, required
- Language name, 64 characters, required
- Short description, one paragraph of text, optional

### *REQ-0.1-B04* Registering speakers

It should be possible to register speakers (or: language consultants), so that we can record in the metadata
from whom information was coming.

Speaker record should consist of:

- Speaker ID, 6 characters, required
- Speaker name, 64 characters, optional
- Speaker sex, masculine, feminine or unknown, optional
- Speaker age, integer or unknown, optional
- Speaker first language, taken from the registered languages, optional
- Other languages spoken by the speaker, list built from registered langauges, optional
- Remarks, paragraph of text,  optional

### *REQ-0.1-B05* Creating projects

User should be able to create projects in the configuration. Project record should contain:

- Unique project ID (GUID), required
- Project schema version, required
- Project name, 32 characters, required
- Project description, one paragraph, optional
- Source language, required
- List of description languages

## Sound recording

### *REQ-0.1-C01* Recorder - tool for sound recording

There should be a tool allowing to record sounds from an external microphone connected to the phone
(eg. a Lavalier microphone).

- It should be possible to save files both to the internal phone memory and to the external memory cards
- It should be possible to monitor sound level during recording
- It should be possible to pause and restart recording with a single tap
- It should be possible to prevent or force turning off of the screen during recording
- It should be possible to monitor recorded sound through headphones
- It should be possible to monitor battery level during recording
- The recording should be saved as uncompressed mono wave file at 44,100 Hz in 16-bit depth,
  registering pitch in human speech up to 20,000 Hz
- When the file is saved, some of the metadata should be automatically added to it:
  - unique ID (GUID), required
  - timestamp, required
  - model of the phone, optional
  - model of the microphone, optional
  - location from GPS, optional
- when the file is saved, user should be able to add list of speakers appearing in the recording
  and one paragraph of description to the metadata 

## Annotating sound recordings

### *REQ-0.1-D01* Defining annotation schema

User should be able to attach custom annotation schema for annotating sounds in the project.
Annotation schema defines annotation groups and annotation layers.
One sound file can have one or more annotation groups.
Segments of the sound file can be defined independently in each group.
Segment stores offsets of its begining end end.

Each group can have many layers. All layers in the same group use the same segments.
Layer has an id unique in the schema and a name (16 chars), both required.
Layers can be of two types: free text or with data coming from the data source.
If the layer uses data from configuration, it must be linked to the data source (eg. language, speaker, vocabulary, phoneme list etc.).
If the layer is free text, it should specify font and keyboard layout to be used for editing data in the layer.

### *REQ-0.1-D02* Importing sounds into project

It should be possible to attach recorded sound files to the project.

### *REQ-0.1-D03* Segmmenting sound file

There should be an editor for segmenting sound files in the project.
It should present the sound as waveform and spectrogram. 
User has to choose annotation group from the annotation schema before segmenting the file.

Editor should allow:

- zooming in and out
- panning
- moving segment boundary
- merging two segments by removing their boundary
- splitting the segment by adding a boundary
- playback of the current segment
- playback of the previous, current and next segment as one group

### *REQ-0.1-D04* Annotating sound file

There should be an editor for entering annotations.
As the user enters the annotation editor, he needs to select a sound file,
view of the sound file (waveform or spectrogram), annotation group and one or more annotation layers.

The editor should allow:

- zooming
- panning
- playback of the current segment
- playback of the previous, current and next segment as one group

### *REQ-0.1-D05* Playback of the sound recording

It should be possible to play the sound file together with annotations being displayed.

When the user enters the player, he needs to choose how the sound is to be represented
(waveform or spectrogram), which group to use for segments and which layers are to be displayed.

Zooming and panning should be available. Selecting spans of recording should be possible.

Playback options:

- whole file
- selection
- one segment at a time

## Exporting and importing data

### *REQ-0.1-E01* Exporting projects to dropbox

It should be possible to export projects to dropbox.
Sound files should be exported in wave format, all other data as JSON.
If export were to overwrite any existing files in the dropbox, user has to confirm the overwrite or choose 
new directory.

### *REQ-0.1-E02* Importing projects from dropbox

It should be possible to import projects from dropbox back into the application.

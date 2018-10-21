import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import  {Modal,Button,Grid, Row, Col,} from 'react-bootstrap';
import Navigation from './Navigation';
import CERouter from './CERouter';

class HomePage extends Component{
  constructor(props){
    super(props);
    this.state={
      baseUrl:"http://localhost:8080/",
      //listofFilesAndFolder:{createdTime:{value:"",dateOnly:"",timeZoneShift:""},id:"",name:""},
      listofFilesAndFolder:[],
      listofFolder:[],
      folderId:"",
      uploadFile:null,
      fileSizeErr:"",
      successmsg:"",
      isOpenModal:false,
      closeModal:false,
      isOpenAuthModal:false,
      closeAuthModal:false,




    }
    this.closeAuthModal=this.closeAuthModal.bind(this);
    this.Authenticate=this.Authenticate.bind(this);
    this.isOpenAuthModal=this.isOpenAuthModal.bind(this);
    this.showDriveFilesAndFolders=this.showDriveFilesAndFolders.bind(this);
    this.downloadItem=this.downloadItem.bind(this);
    this.uploadFile=this.uploadFile.bind(this);
    this.openModal=this.openModal.bind(this);
    this.closeModal=this.closeModal.bind(this);
    this.handleChooseFile=this.handleChooseFile.bind(this);
    this.getFolderList=this.getFolderList.bind(this);
    this.handleselectFolder=this.handleselectFolder.bind(this);







  }
  //isOpenAuthModal
  isOpenAuthModal(){
    this.setState({isOpenAuthModal:true});
  }
  //closeAuthModal
  closeAuthModal(){
    this.setState({isOpenAuthModal:false});
  }
  //Authenticate
  Authenticate(){


    let URLauthenticate=this.state.baseUrl+"authenticate";
    console.log("URLauthenticate:"+URLauthenticate);
        fetch(URLauthenticate,{
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        },

        }).then(function(response) {
            return response.json();
        }).then(response => {
          console.log(JSON.stringify(response));
        });
  }
//popup modal open
openModal(){
  this.getFolderList();
  this.setState({isOpenModal:true});

}
//popup modal close
closeModal(){
  this.setState({isOpenModal:false,uploadFile:null,folderId:"",selectFolderErr:"",successmsg:"",fileSizeErr:""});
}

//handleselectFolder
handleselectFolder=(event)=>{
  this.setState({folderId:event.target.value});

}
//
//handleChooseFile
  handleChooseFile(event){
      const file = event.target.files[0];
            this.setState({
          uploadFile:file,
          });
      }
//getFolderList
getFolderList(){
  let URLgetFolderList=this.state.baseUrl+"getFolderList";
  console.log("URLgetFolderList:"+URLgetFolderList);
      fetch(URLgetFolderList,{
      method: 'GET',
      headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      },

      }).then(function(response) {
          return response.json();
      }).then(response => {
          this.setState({listofFolder:response,
        });
      });

}//End of Method
  //Display All Files and Folders from Google Drive
  showDriveFilesAndFolders=()=>{
    let URLgetAllfilesAndFolder=this.state.baseUrl+"getAllfilesAndFolder";
  console.log("URLgetAllfilesAndFolder:"+URLgetAllfilesAndFolder);
        fetch(URLgetAllfilesAndFolder,{
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        },

        }).then(function(response) {
            return response.json();
        }).then(response => {
            this.setState({listofFilesAndFolder:response,
          });
        });
  }//End Method
//Dowanload Items
downloadItem=(event)=>{
//  alert(event.target.id);

  let URLdownloadfFile=this.state.baseUrl+"downloadfFile?fileOrfolderid="+event.target.id;
  console.log("URLdownloadfFile:"+URLdownloadfFile);
  fetch(URLdownloadfFile,{
    method: 'GET',
    headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    },

    }).then(function(response) {
        return response.json();
    }).then(response => {
      console.log("response:"+response);
    });

}//end method

//uploadFile
uploadFile(){
  this.setState({successmsg:""});
  let uploadFile=this.state.uploadFile;
    if(uploadFile==null){
      this.setState({fileSizeErr:"Please upload file"});
      return;
    }
    if(this.state.folderId==""||this.state.folderId=="0"){
      this.setState({selectFolderErr:"Please Select Folder"});
      return;
    }
  //  alert('Data'+this.state.uploadFilReq.file);
  this.setState({fileSizeErr:"",selectFolderErr:""});
    let formData = new FormData();
 formData.append('file', uploadFile);
    console.log("uploadFile"+formData);
    //let uploadFilReq=this.state.uploadFilReq;
    let URLuploadfFile=this.state.baseUrl+"uploadfFile?googleFolderIdParent="+this.state.folderId+"&&contentType=*/*&&customFileName="+uploadFile.name.replace(/\s/g, "") ;
    console.log(URLuploadfFile);
    fetch(URLuploadfFile,{
    method: 'POST',
    body:formData,
    headers: {
      'Accept': '*/*',
    }}).then(function(response) {
      return response.json()
    }).then(response => {
      console.log("response"+response);
      if(response.errorCode=="0000"){
        this.setState({successmsg:response.errorMessage});
      }else{
          this.setState({successmsg:"File Not Uploaded ,having some internal Issue.."})
      }
      this.setState({uploadFile:null,folderId:"",selectFolderErr:"",fileSizeErr:""});
    });

}

render() {
  const registerModalHeaderStyle={
  fontSize:"16Px",
};
  let middleStyle={
    height:"50px",
    width:"100%",
    font:"30px",
    backgroundColor:"navy",
    border: '0px solid blue',

  }
  let bodycontainerStyle={
    height:"650px",
    width:"100%",
    font:"30px",
    backgroundColor:"#9ba9c1",
    border: '0px solid blue',

  }
  let fileName={
    color:"white",
    height:"30px",

    fontSize:"16px",
    backgroundColor:"#041f3a",

  }
//prepare List Of Folders
let listofFolder=this.state.listofFolder.map((data,index)=>{
return  <option value={data.id} key={index}>{data.name}</option>;
});


//Prepare data to displsy in HTML
let listofData=this.state.listofFilesAndFolder.map((data,index)=>{

  return  <tr key={index}>
          <td style={fileName}>{data.name} </td>
          <buttton className="btn btn-danger  table-hover" id={data.id} onClick={this.downloadItem} >Download</buttton>
        </tr>;

});

    return (
      <div >
  <body>
      <nav className="navbar navbar-inverse">
    <div className="container-fluid">
    <div className="navbar-header">
      <a className="navbar-brand" href="#">Custom Google Drive</a>
    </div>
    <ul className="nav navbar-nav" style={{fontSize:"24px"}}>
      <li className="active"><a href="#">Home</a></li>  </ul>

  </div>
  <div className="container" style={bodycontainerStyle}>
  <div className="row ">
  <div className="col-sm-4 table-responsive">

  <table class="table  ">
      <thead>
        <tr style={{color:"white",fontSize:"24px",}}>
          <th>Action  </th>
        </tr>
      </thead>
      <tbody>
      <tr><td> <button type="button" class="btn btn-success" onClick={this.isOpenAuthModal}>Authenticate Google Drive Account First </button></td></tr>
      <tr><td>  <button type="button" class="btn btn-success" onClick={this.showDriveFilesAndFolders}>Show Your Google Drive Files and Folder</button></td></tr>
      <tr><td> <button type="button" class="btn btn-success" onClick={this.openModal}>Upload File</button></td></tr>
      <tr><td></td></tr>
  </tbody>
  </table>
  </div>
  <div className="col-sm-8" style={{ overflow:"auto", height: "600px"}}>
  <table class="table">
      <thead>
        <tr style={{color:"white",fontSize:"24px",}}>
          <th>File/Folder Name </th>
          <th>Download</th>
        </tr>
      </thead>
      <tbody>
  {listofData}
  </tbody>
</table>
</div>
</div>
</div>
</nav>



   </body>


  <Modal
  {...this.props}
  aria-labelledby="contained-modal-title-sm"
  show={this.state.isOpenModal}
  onHide={this.closeModal}
  backdrop={"static"}

  >
        <Modal.Header style={{backgroundColor:"#9966ff"}}>
          <div className="col-sm-11 text-center" style={registerModalHeaderStyle}>
              <span style={{color:"black",fontSize:"20px"}}>Upload File in Specific Folder</span>
          </div>
           <div className="col">
             <button type="button" className="close" onClick={this.closeModal}>&times;</button>
           </div>
        </Modal.Header>
        <Modal.Body>
        <div className="container-fluid">

            <div className="form-group row">
            <label for="fName" style={{fontSize:"20px",color:"black"}}>Upload in:</label>
            <select   onChange={this.handleselectFolder} style={{width:"200px",height:"30px"}}>
              <option value="0">Select Folder</option>
              {listofFolder}
            </select>
             <span style={{color:"red"}}>{this.state.selectFolderErr}</span>

            </div>
            <div className="form-group row">
               <input   type="file" class="form-control" name="files[]" placeholder='Choose a file...' onChange={this.handleChooseFile}/>  </div>
               <span style={{color:"red"}}>{this.state.fileSizeErr}</span>
               <span style={{color:"red"}}>{this.state.successmsg}</span>

          </div>
        </Modal.Body>
        <Modal.Footer>
        <div  className="row "  >
          <div className="col-sm-6 " style={{marginLeft:"-100px"}}>
           <Button className="btn btn-primary " onClick={this.uploadFile}>
                Upload
            </Button>
          </div>
          <div className="col-sm-6" >
            <Button className="btn btn-primary" onClick={this.closeModal}>
                Cancel
            </Button>
          </div>
          </div>
        </Modal.Footer>
    </Modal>
    {/*Authenticate Modal*/}
    <Modal
    {...this.props}
    aria-labelledby="contained-modal-title-sm"
    show={this.state.isOpenAuthModal}
    onHide={this.closeAuthModal}
    backdrop={"static"}

    >
          <Modal.Header style={{backgroundColor:"#9966ff"}}>
            <div className="col-sm-11 text-center" style={registerModalHeaderStyle}>
                <span style={{color:"black",fontSize:"20px"}}>Authenticate Your self with Google Drive</span>
            </div>
             <div className="col">
               <button type="button" className="close" onClick={this.closeAuthModal}>&times;</button>
             </div>
          </Modal.Header>
          <Modal.Body>
          <div className="container-fluid">

              <div className="form-group row">
              <label for="fName" style={{fontSize:"16px",color:"black"}}>Please Follow The Instructions  </label>
              <h5>ENABLE THE DRIVE API</h5>
              <h5>This opens a new dialog. In the dialog, do the following:</h5>
              <h5>Select + Create a new project.</h5>
              <h5>Download the configuration file.</h5>
              <h5>Create Folder  called "credentials" in src/main/resources/ </h5>

              <h4>Move the downloaded file to this project src/main/resources/credentials/ credentials.json</h4>
              <h4>After placing credentials.json into src/main/resources/credentials/ check by "Check  Authenticated?" Button</h4>
              <a href="https://developers.google.com/drive/api/v3/quickstart/java" target="_blank">ENABLE THE DRIVE API</a>
              </div>

            </div>
          </Modal.Body>
          <Modal.Footer>
          <div  className="row "  >
            <div className="col-sm-6 " style={{marginLeft:"-100px"}}>
             <Button className="btn btn-primary " onClick={this.Authenticate}>
              Check  Authenticated?
              </Button>
            </div>
            <div className="col-sm-6" >
              <Button className="btn btn-primary" onClick={this.closeAuthModal}>
                  Cancel
              </Button>
            </div>
            </div>
          </Modal.Footer>
      </Modal>

    </div>  );
}
}
export default HomePage;

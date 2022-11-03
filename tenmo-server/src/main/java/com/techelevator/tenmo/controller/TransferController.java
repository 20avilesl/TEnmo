package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/transfers/")
@RestController
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }
    @ApiOperation("update the transfer status.")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public boolean updateTransferStatus(@ApiParam("enter transfer id") @PathVariable int id, @ApiParam("enter status") @RequestBody String status){

        return transferDao.updateTransferStatus(id, status);
    }

    @ApiOperation("Create transfer")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createTransfer(@ApiParam("enter sender id") @RequestBody Transfer transfer){

        if(!transferDao.createTransfer(transfer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create Transfer failed.");
        }
    }
    //TODO only allow authenticated users to see their own transfers.

    @ApiOperation("Get all Transfers")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@ApiParam("enter username")@RequestParam String username){
        return transferDao.findAllTransfers(username);
    }

}


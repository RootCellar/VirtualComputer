#!/bin/bash
#Follow motherboard log to get some live, verbose info on the screen

tail --follow=name ../build/Logs/Assembler/Assembler.txt

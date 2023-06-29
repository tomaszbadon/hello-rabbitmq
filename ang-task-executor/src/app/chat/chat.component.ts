import { Component, OnInit } from '@angular/core';
import { Message } from '../Message';
import { RxStomp } from '@stomp/rx-stomp';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.sass']
})
export class ChatComponent implements OnInit {

  messages: Message[] = [];

  rxStomp: RxStomp = new RxStomp();

  ngOnInit(): void {
    
    this.rxStomp.configure({
      brokerURL: 'ws://localhost:15674/ws',
    });

    this.rxStomp.activate();

    const subscription = this.rxStomp.watch({ destination: "/amq/queue/incoming" })
      .subscribe((message) => {
        let incommingMessage: Message = JSON.parse(message.body);
        this.messages.push(incommingMessage);
      });
  }



}

#!/usr/bin/perl
{
    package MyWebServer;

    use strict;
    use warnings FATAL => 'all';

    use HTTP::Server::Simple::CGI;
    use base qw(HTTP::Server::Simple::CGI);

    my %dispatch = (
        '/hello' => \&resp_hello,
        # ...
    );

    sub handle_request {
        my $self = shift;
        my $cgi  = shift;

        my $path = $cgi->path_info();
        my $handler = $dispatch{$path};

        if (ref($handler) eq "CODE") {
            print "HTTP/1.0 200 OK\r\n";
            $handler->($cgi);

        } else {
            print "HTTP/1.0 404 Not found\r\n";
            print $cgi->header,
                $cgi->start_html('Not found'),
                $cgi->h1('Not found'),
                $cgi->end_html;
        }
    }

    sub resp_hello {
        my $cgi  = shift;   # CGI.pm object
        return if !ref $cgi;

        my $who = $cgi->param('name');

        print $cgi->header('application/json'),'{ "response": "blah" }';
    }

}

# start the server on port 31820
my $pid = MyWebServer->new(31820)->background();
print "Use 'kill $pid' to stop server.\n";
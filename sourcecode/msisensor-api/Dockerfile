FROM ubuntu:18.10

RUN apt-get update && \
  apt-get install -y git make build-essential zlib1g-dev libncurses5-dev libncursesw5-dev && \
  apt-get install -y python3-pip python3-dev

RUN mkdir -p /data/msisensor/incoming && \
  mkdir -p /data/msisensor/outgoing && \
  mkdir /logs

RUN cd /tmp \
  && git clone https://github.com/ding-lab/msisensor.git msisensor_install_dir \
  && cd msisensor_install_dir \
  && git checkout 0.6 \
  && make \
  && mv /tmp/msisensor_install_dir/msisensor /usr/bin/

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

RUN pip3 install -r requirements.txt

ENTRYPOINT [ "python3" ]

CMD [ "main.py" ]
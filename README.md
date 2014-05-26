# JSOS-OE-Core

JSOS-OE-Core is a [Yocto/OpenEmbedded](https://www.yoctoproject.org) compliant
layers to ease building of JSOS images on different hardware setups.

Please note that all the layers nested within this project follow the
normal Yocto/OpenEmbedded conventions with their plaintext READMEs etc.

For more information on the stack, please see
[JSOS home page](http://js-os.org/).

## Installation and Usage

### Install the toolchain

In order to build images with Yocto, you need the Bitbake build tool and
OpenEmbedded oe-core and meta-yocto layers for the basic boilerplates. You can
obtain these by cloning the Yocto repo 'poky' boilerplate:

    git clone git://git.yoctoproject.org/poky yocto
    cd yocto
   
### Obtain JSOS layers

Once you have a Yocto setup, you can clone the JSOS layers on top of it. Please
note that you do not need to necessarily clone them into yocto directory, if
you add a symlink to the cloned directory within the yocto directory.

    git clone https://github.com/js-os/jsos-oe-core.git

### Initialise build environment

Then initialise the Yocto build environment in 'yocto' directory. This will
create a 'build' directory where the build results and working environment will
be placed.

    source oe-init-build-env

You can add an extra command line argument to create it somewhere
else than 'build', e.g. 'source oe-init-build-env jsos'. After the environment
is created, it will take you to the directory.

### Configure the build

The next step is to quickly check what are we building. Open the build
configuration in your favourite editor, and check the machine architecture
(MACHINE variable) is set for 'qemux86', e.g. for an emulator.

    nano conf/local.conf

There are several things you want to do later on, like switch the machine
architecture from QEMU to an embedded device, or speed up build. Check these at
[Yocto project quick start](http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html).

Then modify the boilerplate build to recognise JSOS meta layer:

    nano conf/bblayers.com

Add the JSOS meta layer to the layer list e.g.

    BBLAYERS ?= " \
      /home/lauri/yocto/meta \
      /home/lauri/yocto/meta-yocto \
      /home/lauri/yocto/meta-yocto-bsp \
      /home/lauri/yocto/meta-jsos \
      "

### Fetch build tools

Yocto depends on several build tools, and the actual dependencies may vary on
different distributions. The following example is for Ubuntu 12.10.
Check the up-to-date documentation at
[Yocto project quick start](http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html).

    sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib \
      build-essential chrpath libsdl1.2-dev xterm

### Build JSOS

Now we are finally ready to do the build:

    bitbake jsos-minimal

The first build will cache hundreds of Linux packages and may take several
hours.

### Running the image on emulator

The image will be built in '$BUILDDIR/tmp/deploy/images/qemux86' (when your
target machine is 'qemux86'. There will be a separate kernel image, root file
system (and a few other files, like kernel modules). To start qemu, pass it a
valid kernel and a valid root file system:

    runqemu tmp/deploy/images/qemux86/bzImage-qemux86.bin \
      tmp/deploy/images/qemux86/jsos-minimal-qemux86.ext3

The kernel should boot right into JSOS init stub. On a successful boot you
should see node.js REPL where you can start typing your JavaScript commands:

    jsos>

Happy hacking!
